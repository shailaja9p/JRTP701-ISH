package com.lifetree.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CaseWorkerData {
	private Integer accountId;
	private String fullName;
	private String email;
	private String password;
	private String gender;
	private Integer SSN;
	private LocalDate dob;
}
