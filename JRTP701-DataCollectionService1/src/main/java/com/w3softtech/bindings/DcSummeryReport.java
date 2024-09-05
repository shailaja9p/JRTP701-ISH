package com.w3softtech.bindings;

import java.util.List;

import lombok.Data;

@Data
public class DcSummeryReport{

	private EducationInputs educationDetails;
	private IncomeInputs incomeDetails;
	private List<ChildInputs> childDetails;
	private CitizenAppRegistrationInputs  citizenAppRegDetails;
	private String planName;
}
