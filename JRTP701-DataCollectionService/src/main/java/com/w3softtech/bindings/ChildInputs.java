package com.w3softtech.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ChildInputs {

	private Integer childId;
	private Integer caseNo;
	private LocalDate childDob;
	private Long childSSN;
}
