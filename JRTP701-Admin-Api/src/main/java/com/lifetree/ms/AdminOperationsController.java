package com.lifetree.ms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifetree.bindings.CaseWorkerData;
import com.lifetree.bindings.PlanData;
import com.lifetree.service.IAdminMngmtService;

import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.XML;

@RestController
@RequestMapping("/admin-api")
public class AdminOperationsController {

	@Autowired
	private IAdminMngmtService adminService;

	@GetMapping("/categories")
	public ResponseEntity<Map<Integer, String>> showPlanCategories() {
		Map<Integer, String> planCategories = adminService.getPlanCategories();
		return new ResponseEntity<Map<Integer, String>>(planCategories, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<String> savePlan(@RequestBody PlanData plan) {
		String msg = adminService.registerPlan(plan);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}

	@GetMapping(path="/showAllPlans",produces = {"application/xml","application/json"})
	public ResponseEntity<?> getAllPlans() {
		List<PlanData> allPlans = adminService.showAllPlans();
		return new ResponseEntity<List<PlanData>>(allPlans, HttpStatus.OK);
	}

	@GetMapping(path="/findPlan/{planId}",produces = {"application/json"})
	public ResponseEntity<PlanData> showAllPlanById(@PathVariable Integer planId) {
		PlanData plan = adminService.showPlanById(planId);
		return new ResponseEntity<PlanData>(plan, HttpStatus.OK);
	}

	@PutMapping("/updatePlan")
	public ResponseEntity<String> updatePlan(@RequestBody PlanData plan) {
		String msg = adminService.updatePlan(plan);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	@DeleteMapping("/deletePlan/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId) {
		String msg = adminService.deletePlan(planId);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	@PutMapping("/status-change/{planId}/{status}")
	public ResponseEntity<String> changePlanStatus(@PathVariable Integer planId, @PathVariable String status) {
		String msg = adminService.changePlanStatus(planId, status);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	//// case worker CURD operations
	@PostMapping("/registerCaseworker")
	public ResponseEntity<String> saveCaseWorker(@RequestBody CaseWorkerData data){
		String worker = adminService.saveCaseWorker(data);
		return new ResponseEntity<String>(worker,HttpStatus.CREATED);
	}
	@GetMapping(path="/showAllCaseWorkers",produces = {"application/xml","application/json"})
	public ResponseEntity<List<CaseWorkerData>> showAllCaseWorkers(){
		List<CaseWorkerData> caseWorkers = adminService.showAllCaseWorkers();
		return new ResponseEntity<List<CaseWorkerData>>(caseWorkers,HttpStatus.OK);
	}
	@GetMapping(path="/findCaseWorkerById/{cwId}",produces = {"application/xml"})
	public ResponseEntity<CaseWorkerData> showCaseWorkerById(@PathVariable Integer cwId) {
		CaseWorkerData data = adminService.showCaseWorkerById(cwId);
		return new ResponseEntity<CaseWorkerData>(data,HttpStatus.OK);
	}
	@PutMapping("/updateCaseworker")
	public ResponseEntity<String> updateCaseWorker(@RequestBody CaseWorkerData data){
		String worker = adminService.updateCaseWorker(data);
		return new ResponseEntity<String>(worker,HttpStatus.CREATED);
	}
	@DeleteMapping("/deleteCaseworker/{cwId}")
	public ResponseEntity<String> deleteCaseworker(@PathVariable Integer cwId){
		String worker = adminService.deleteCaseworker(cwId);
		return new ResponseEntity<String>(worker,HttpStatus.CREATED);
	}
}
