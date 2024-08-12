package com.w3softtech.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w3softtech.binding.CitizenAppRegistrationInputs;
import com.w3softtech.service.ICitizenApplicationRegistrationService;

@RestController
@RequestMapping("/CitizenAR-api")
public class CitizenAppRegistartionsOperationsController {

	@Autowired
	private ICitizenApplicationRegistrationService registrationService;

	@PostMapping("/save")
	public ResponseEntity<String> saveCitizenapplication(@RequestBody CitizenAppRegistrationInputs inputs){
		try {
			Integer appId = registrationService.regiserCitizenApplication(inputs);
			if(appId>0)
				return new ResponseEntity<String>("Citizen Application is Registered with Id:: "+appId,HttpStatus.CREATED);
			else
				return new ResponseEntity<String>("Invalid SSN/Citizen must belong to California State",HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
}
