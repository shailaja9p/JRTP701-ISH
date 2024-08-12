package com.w3softtech.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.w3softtech.binding.CitizenAppRegistrationInputs;
import com.w3softtech.entity.CitizenAppRegistartionEntity;
import com.w3softtech.repository.IApplicationRegistrationRepository;

@Service
public class CitizenApplicationRegistrationServiceImpl implements ICitizenApplicationRegistrationService {

	@Autowired
	private IApplicationRegistrationRepository citizenRepo;
	
	@Autowired
	private RestTemplate template;
	@Value("${ar.ssa-web.url}")
	private String endPointUrl;
	@Value("${ar.state}")
	private String targetSate;
	
	@Override
	public Integer regiserCitizenApplication(CitizenAppRegistrationInputs inputs) {

		 ResponseEntity<String> response= template.exchange(endPointUrl, HttpMethod.GET,null,String.class,inputs.getSsn());
		 
		 String stateName= response.getBody();
		 
		 if(stateName.equalsIgnoreCase(targetSate)) {
			 
			 CitizenAppRegistartionEntity entity = new CitizenAppRegistartionEntity();
			 BeanUtils.copyProperties(inputs, entity);
			 entity.setStateName(stateName);
			 
			 int appId= citizenRepo.save(entity).getAppId();
			 return appId;
		 }
		
		return 0;
	}

}
