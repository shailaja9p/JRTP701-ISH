package com.w3softtech.ms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w3softtech.bindings.ChildInputs;
import com.w3softtech.bindings.DcSummeryReport;
import com.w3softtech.bindings.EducationInputs;
import com.w3softtech.bindings.IncomeInputs;
import com.w3softtech.bindings.PlanSelectionInputs;
import com.w3softtech.entity.DcIncomeEntity;
import com.w3softtech.service.IDcMngtService;

@RestController
@RequestMapping("/dc-api")
public class DataCollectionOprationsController {

	@Autowired
	private IDcMngtService dataMngtService;
	
	@PostMapping("/generateCaseNo/{appId}")
	public ResponseEntity<Integer> generateCaseNumber(@PathVariable Integer appId){
		Integer caseNumber = dataMngtService.genearteCaseNumber(appId);
		return new ResponseEntity<Integer>(caseNumber,HttpStatus.CREATED);
	}
	
	@GetMapping("/planNames")
	public ResponseEntity<List<String>> displayPlanNames(){
		List<String> planNamesList = dataMngtService.showAllPlanNames();
		return new ResponseEntity<List<String>>(planNamesList,HttpStatus.OK);
	}
	@PutMapping("/updatePlanSelection")
	public ResponseEntity<Integer> savePlanSelection(@RequestBody PlanSelectionInputs plan){
		Integer caseNo = dataMngtService.savePlanSelection(plan);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.OK);
	}
	@PostMapping("/saveIncome")
	public ResponseEntity<Integer> saveIncomeDetails(@RequestBody IncomeInputs income){
		Integer caseNo = dataMngtService.saveIncomeDetails(income);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.CREATED);
	}
	@PostMapping("/saveEducation")
	public ResponseEntity<Integer> saveEducationDetails(@RequestBody EducationInputs education){
		Integer caseNo = dataMngtService.saveEducationDetails(education);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.OK);
	}
	@PostMapping("/saveChildren")
	public ResponseEntity<Integer> saveChildrenDetails(@RequestBody List<ChildInputs> children){
		Integer caseNo = dataMngtService.saveChildrenDetails(children);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.OK);
	}
	@GetMapping("/showReport/{caseNo}")
	public ResponseEntity<DcSummeryReport> showReport(@PathVariable Integer caseNo){
		DcSummeryReport report = dataMngtService.showDcSummery(caseNo);
		return new ResponseEntity<DcSummeryReport>(report,HttpStatus.OK);
	}
	//public DcIncomeEntity showDcIncomeEntity(Integer caseNo) {
	@GetMapping("/incomeDetails/{caseNo}")
	public ResponseEntity<DcIncomeEntity> showDcIncomeEntity(@PathVariable Integer caseNo){
		DcIncomeEntity income = dataMngtService.showDcIncomeEntity(caseNo);
		return new ResponseEntity<DcIncomeEntity>(income,HttpStatus.OK);
	}
}
