package com.w3softtech.service;

import com.w3softtech.binding.CitizenAppRegistrationInputs;
import com.w3softtech.exceptions.SSNNotFoundException;

public interface ICitizenApplicationRegistrationService {

	public Integer regiserCitizenApplication(CitizenAppRegistrationInputs inputs) throws SSNNotFoundException;
}
