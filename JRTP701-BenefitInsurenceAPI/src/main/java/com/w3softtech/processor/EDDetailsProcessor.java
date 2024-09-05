package com.w3softtech.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.w3softtech.entity.EligibilityDetails;
import com.w3softtech.entity.EligibilityDetailsEntity;

@Component
public class EDDetailsProcessor implements ItemProcessor<EligibilityDetailsEntity, EligibilityDetails> {

	@Override
	public EligibilityDetails process(EligibilityDetailsEntity item) throws Exception {
		if(item.getPlanStatus().equalsIgnoreCase("approved")) {
			EligibilityDetails details=new EligibilityDetails();
			BeanUtils.copyProperties(item, details);
			return details;
		}
		return null;
	}

}
