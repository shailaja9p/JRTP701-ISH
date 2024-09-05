package com.w3softtech.entity;

import lombok.Data;
@Data
public class EligibilityDetails {
	private Integer caseNo;
	private String holderName;
	private Long holderSSN;
	private String planName;
	private String planStatus;
	private Double benifitAmt;
	private String bankName;
	private Long accountNo;
}
