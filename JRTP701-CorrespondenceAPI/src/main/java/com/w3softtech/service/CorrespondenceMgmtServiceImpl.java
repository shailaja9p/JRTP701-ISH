package com.w3softtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.w3softtech.binding.COSummery;
import com.w3softtech.entity.CoTriggersEntity;
import com.w3softtech.entity.repository.ICOTriggerRepository;

@Service
public class CorrespondenceMgmtServiceImpl implements ICorrespondenceMgmtService {

	@Autowired
	private ICOTriggerRepository triggerRepo;
	
	@Override
	public COSummery processPendingTriggers() {
		List<CoTriggersEntity> list = triggerRepo.findByTriggerStatus("pending");
		for(CoTriggersEntity entity:list) {
			
		}
		return null;
	}

}
