package com.w3softtech.service;

import org.springframework.batch.core.JobExecution;

public interface IBenefitInsurenceMgmtService {
public JobExecution sendAmountToBenefiatories() throws Exception;
}
