package com.w3softtech.service;

import com.w3softtech.bindings.EligibilityDetailsOutput;

public interface IEligibilityDeterminationMgmtService {

	public EligibilityDetailsOutput determineEligibility(Integer cNo);
	
}
