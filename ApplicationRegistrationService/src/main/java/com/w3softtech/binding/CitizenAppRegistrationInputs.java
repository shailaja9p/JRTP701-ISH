package com.w3softtech.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitizenAppRegistrationInputs {
	private String fullName;
	private String email;
	private String gender;
	private Long phoneNo;
	private Long ssn;
	private LocalDate dob;
}
