package com.lifetree.service;

import java.util.List;
import java.util.Map;

import com.lifetree.bindings.CaseWorkerData;
import com.lifetree.bindings.PlanData;

public interface IAdminMngmtService {

	// curd operations related to PlanEntity
		public String registerPlan(PlanData tp);
		public Map<Integer,String> getPlanCategories();
		public List<PlanData> showAllPlans();
		public PlanData showPlanById(Integer planId);
		public String updatePlan(PlanData plan);
		public String deletePlan(Integer planId);
		public String changePlanStatus(Integer planId,String status);
		
		//curd operations related to case workers
		public String saveCaseWorker(CaseWorkerData worker);
		public List<CaseWorkerData> showAllCaseWorkers();
		public CaseWorkerData showCaseWorkerById(Integer cwId);
		public String updateCaseWorker(CaseWorkerData worker);
		public String deleteCaseworker(Integer cwId);
}
