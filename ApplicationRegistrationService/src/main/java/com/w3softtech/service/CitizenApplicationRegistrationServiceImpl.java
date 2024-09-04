package com.w3softtech.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.w3softtech.binding.CitizenAppRegistrationInputs;
import com.w3softtech.entity.CitizenAppRegistartionEntity;
import com.w3softtech.exceptions.SSNNotFoundException;
import com.w3softtech.repository.IApplicationRegistrationRepository;

import reactor.core.publisher.Mono;

@Service
public class CitizenApplicationRegistrationServiceImpl implements ICitizenApplicationRegistrationService {

	@Autowired
	private IApplicationRegistrationRepository citizenRepo;
	
	//@Autowired
//	private RestTemplate template;
	
	@Autowired
	private WebClient client;
	@Value("${ar.ssa-web.url}")
	private String endPointUrl;
	@Value("${ar.state}")
	private String targetSate;
	
	/*@Override
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
		
	}*/
	@Override
	public Integer regiserCitizenApplication(CitizenAppRegistrationInputs inputs) throws SSNNotFoundException{

		//String stateName = client.get().uri(endPointUrl,inputs.getSsn()).retrieve().bodyToMono(String.class).block();
		/*
		 * String stateName = client.get() .uri(endPointUrl,inputs.getSsn()) .exchange()
		 * .flatMap(clientResponse -> clientResponse.bodyToMono(String.class)).block();
		 */
		Mono<String> response = client.get()
																	.uri(endPointUrl, inputs.getSsn()).retrieve()
																	.onStatus(HttpStatus.BAD_REQUEST::equals,
																			res -> res.bodyToMono(String.class)
																			.flatMap(errorBody -> Mono.error(new SSNNotFoundException(errorBody))))
																	.bodyToMono(String.class);                   
		
		String stateName= response.block();
	
		 if(stateName.equalsIgnoreCase(targetSate)) {
			 
			 CitizenAppRegistartionEntity entity = new CitizenAppRegistartionEntity();
			 BeanUtils.copyProperties(inputs, entity);
			 entity.setStateName(stateName);
			 
			 int appId= citizenRepo.save(entity).getAppId();
			 return appId;
		 }
		
		throw new SSNNotFoundException("Invalid SSN Entered");
	}

	 
}
