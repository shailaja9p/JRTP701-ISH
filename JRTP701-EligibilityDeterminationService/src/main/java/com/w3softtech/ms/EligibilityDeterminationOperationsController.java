package com.w3softtech.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w3softtech.bindings.EligibilityDetailsOutput;
import com.w3softtech.service.IEligibilityDeterminationMgmtService;

@RestController
@RequestMapping("/ed-api")
public class EligibilityDeterminationOperationsController {
	
	@Autowired
	private IEligibilityDeterminationMgmtService eligibiltyService;

	@GetMapping("/determineEligibility/{caseNo}")
	public ResponseEntity<EligibilityDetailsOutput> findEligibility(@PathVariable Integer caseNo){
		EligibilityDetailsOutput output = eligibiltyService.determineEligibility(caseNo);
		return new ResponseEntity<EligibilityDetailsOutput>(output,HttpStatus.CREATED);
	}
}
