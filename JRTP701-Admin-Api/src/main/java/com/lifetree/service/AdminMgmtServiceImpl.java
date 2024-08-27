package com.lifetree.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifetree.bindings.CaseWorkerData;
import com.lifetree.bindings.PlanData;
import com.lifetree.config.AppConfigProperties;
import com.lifetree.constants.PlanConstants;
import com.lifetree.entity.CaseWorkerEntity;
import com.lifetree.entity.PlanCategory;
import com.lifetree.entity.PlanEntity;
import com.lifetree.repository.ICaseworkerRepository;
import com.lifetree.repository.IPlanCategoryRepository;
import com.lifetree.repository.IPlanRepository;

@Service
public class AdminMgmtServiceImpl implements IAdminMngmtService {

	private Map<String, String> messages;

	@Autowired
	public AdminMgmtServiceImpl(AppConfigProperties props) {
		messages = props.getMessages();
	}

	@Autowired
	private IPlanRepository planRepo;
	@Autowired
	private IPlanCategoryRepository planCategoryRepo;
	@Autowired
	private ICaseworkerRepository caseworkerRepo;

	@Override
	public String registerPlan(PlanData plan) {
		PlanEntity entity = new PlanEntity();
		BeanUtils.copyProperties(plan, entity);
		PlanEntity planEntity = planRepo.save(entity);
		return planEntity.getPlanId() != null ? messages.get(PlanConstants.SAVESUCCESS) + " : " + planEntity.getPlanId()
				: messages.get(PlanConstants.SAVEFAILURE);
	}

	@Override
	public Map<Integer, String> getPlanCategories() {
		List<PlanCategory> list = planCategoryRepo.findAll();

		System.out.println(list);

		Map<Integer, String> categoriesMap = planCategoryRepo.findAll().stream()
				.collect(Collectors.toMap(PlanCategory::getCategoryId, PlanCategory::getCategoryName));
		return categoriesMap;
	}

	@Override
	public List<PlanData> showAllPlans() {

		List<PlanEntity> listEntites = planRepo.findAll();
		List<PlanData> listData = new ArrayList<>();
		listEntites.forEach(entity -> {
			PlanData data = new PlanData();
			BeanUtils.copyProperties(entity, data);
			listData.add(data);
		});
		return listData;
	}

	/*
	 * @Override public TravelPlan showTravelPlanById(Integer planId) { return
	 * travelPlanRepo.findById(planId).orElseThrow(()->new
	 * IllegalArgumentException("Travel plan is not found")); }
	 */
	@Override
	public PlanData showPlanById(Integer planId) throws IllegalArgumentException {
		PlanEntity planEntity = planRepo.findById(planId).get();
		PlanData data = new PlanData();
		BeanUtils.copyProperties(planEntity, data);
		return data;
	}

	@Override
	public String updatePlan(PlanData plan) {
		Optional<PlanEntity> optEntity = planRepo.findById(plan.getPlanId());
		if (optEntity.isPresent()) {
			PlanEntity planEntity = optEntity.get();
			BeanUtils.copyProperties(plan, planEntity);
			PlanEntity saved = planRepo.save(planEntity);
			return messages.get(PlanConstants.UPDATESUCCESS) + " : " + plan.getPlanId();
		} else
			return messages.get(PlanConstants.UPDATEFAILURE) + plan.getPlanId();
	}

	@Override
	public String deletePlan(Integer planId) {
		Optional<PlanEntity> opt = planRepo.findById(planId);
		if (opt.isPresent()) {
			planRepo.deleteById(planId);
			return messages.get(PlanConstants.DELETESUCCESS) + " : " + planId;
		} else
			return messages.get(PlanConstants.DELETEFAILURE) + " : " + planId;
	}

	@Override
	public String changePlanStatus(Integer planId, String status) {
		Optional<PlanEntity> optEntity = planRepo.findById(planId);
		if (optEntity.isPresent()) {
			PlanEntity entity = optEntity.get();
			entity.setActiveSw(status);
			planRepo.save(entity);
			return planId + " : " + messages.get(PlanConstants.UPDATESUCCESS);
		} else
			return planId + " : " + messages.get(PlanConstants.UPDATEFAILURE);
	}
//////////////////////////curd operations related to case workers
	@Override
	public String saveCaseWorker(CaseWorkerData worker) {
		CaseWorkerEntity workerEntity = new CaseWorkerEntity();
		BeanUtils.copyProperties(worker, workerEntity);
		CaseWorkerEntity savedWorker = caseworkerRepo.save(workerEntity);
		return savedWorker.getAccountId() != null ? "Case worker is saved with id : " + savedWorker.getAccountId()
				: "Problem in saving case worker";
	}

	@Override
	public List<CaseWorkerData> showAllCaseWorkers() {
		List<CaseWorkerEntity> listEntity = caseworkerRepo.findAll();
		List<CaseWorkerData> listData = new ArrayList<>();
		listEntity.forEach(entity -> {
			CaseWorkerData data = new CaseWorkerData();
			BeanUtils.copyProperties(entity, data);
			listData.add(data);
		});
		return listData;
	}

	@Override
	public CaseWorkerData showCaseWorkerById(Integer cwId) {
		CaseWorkerEntity optEntity = caseworkerRepo.findById(cwId).get();
		CaseWorkerData data = new CaseWorkerData();
		BeanUtils.copyProperties(optEntity, data);
		return data;
	}

	@Override
	public String updateCaseWorker(CaseWorkerData worker) {
		Optional<CaseWorkerEntity> optEntity = caseworkerRepo.findById(worker.getAccountId());
		if (optEntity.isPresent()) {
			CaseWorkerEntity entity = new CaseWorkerEntity();
			BeanUtils.copyProperties(worker, entity);
			caseworkerRepo.save(entity);
			return "caseworker is updated ";
		} else
			return "caseworker is not found for updation";
	}

	@Override
	public String deleteCaseworker(Integer cwId) {
		Optional<CaseWorkerEntity> optEntity = caseworkerRepo.findById(cwId);
		if (optEntity.isPresent()) {
			caseworkerRepo.deleteById(cwId);
			return "caseworker is deleted";
		}
		return "caseworker is not found for deletion";
	}
}
