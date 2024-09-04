package com.w3softtech.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w3softtech.binding.COSummery;
import com.w3softtech.service.ICorrespondenceMgmtService;

@RestController
@RequestMapping("/co-triggers-api")
public class CoTriggersOperationsController {

	@Autowired
	private ICorrespondenceMgmtService coService;
	
	//public COSummery processPendingTriggers();
	@GetMapping("/process")
	public ResponseEntity<COSummery> processAndUpdateTriggers(){
		COSummery summery = coService.processPendingTriggers();
		return new ResponseEntity<COSummery>(summery,HttpStatus.OK);
	}
}
