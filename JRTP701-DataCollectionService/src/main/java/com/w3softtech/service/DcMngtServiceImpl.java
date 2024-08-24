package com.w3softtech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.w3softtech.bindings.ChildInputs;
import com.w3softtech.bindings.CitizenAppRegistrationInputs;
import com.w3softtech.bindings.DcSummeryReport;
import com.w3softtech.bindings.EducationInputs;
import com.w3softtech.bindings.IncomeInputs;
import com.w3softtech.bindings.PlanSelectionInputs;
import com.w3softtech.entity.CitizenAppRegistartionEntity;
import com.w3softtech.entity.DcCaseEntity;
import com.w3softtech.entity.DcChildrenEntity;
import com.w3softtech.entity.DcEducationEntity;
import com.w3softtech.entity.DcIncomeEntity;
import com.w3softtech.entity.PlanEntity;
import com.w3softtech.repository.IApplicationRegistrationRepository;
import com.w3softtech.repository.IDcCaseRepository;
import com.w3softtech.repository.IDcChildrenRepository;
import com.w3softtech.repository.IDcEducationRepository;
import com.w3softtech.repository.IDcIncomeRepository;
import com.w3softtech.repository.IPlanRepository;

@Service
public class DcMngtServiceImpl implements IDcMngtService {

	@Autowired
	private IApplicationRegistrationRepository appRepo;

	@Autowired
	private IDcCaseRepository caseRepo;

	@Autowired
	private IPlanRepository planRepo;

	@Autowired
	private IDcIncomeRepository incomeRepo;

	@Autowired
	private IDcEducationRepository eduRepo;

	@Autowired
	private IDcChildrenRepository childrenRepo;

	@Autowired
	private IApplicationRegistrationRepository citizenAppRepo;

	@Override
	public Integer genearteCaseNumber(Integer appId) {
		// load citizen data
		Optional<CitizenAppRegistartionEntity> opt = appRepo.findById(appId);
		if (opt.isPresent()) {
			// as part of save operation we are saving app id in DCCaseEntity but not saving
			// plan id.
			DcCaseEntity caseEntity = new DcCaseEntity();
			caseEntity.setAppId(appId);
			return caseRepo.save(caseEntity).getCaseNo(); // save object operation. partial save ( plan id is not saved)
		}
		return 0;
	}

	@Override
	public List<String> showAllPlanNames() {
		return planRepo.findAll().stream().map(plan -> plan.getPlanName()).toList();
	}

	@Override
	public Integer savePlanSelection(PlanSelectionInputs plan) {
		Optional<DcCaseEntity> opt = caseRepo.findById(plan.getCaseNo());
		if (opt.isPresent()) {
			// as part of update operation we are saving plan id in DCCaseEntity.
			DcCaseEntity caseEntity = opt.get();
			caseEntity.setPlanId(plan.getPlanId());
			return caseRepo.save(caseEntity).getCaseNo(); // update object operation
		}
		return 0;
	}

	@Override
	public Integer saveIncomeDetails(IncomeInputs income) {
		DcIncomeEntity incomeEntity = new DcIncomeEntity();
		BeanUtils.copyProperties(income, incomeEntity);
		incomeRepo.save(incomeEntity);
		return income.getCaseNo();

	}

	@Override
	public Integer saveEducationDetails(EducationInputs education) {
	DcEducationEntity eduEntity= new DcEducationEntity();
		BeanUtils.copyProperties(education, eduEntity);
		eduRepo.save(eduEntity);
		return education.getCaseNo();
	}

	@Override
	public Integer saveChildrenDetails(List<ChildInputs> children) {
		children.forEach(child -> {
			DcChildrenEntity entity = new DcChildrenEntity();
			BeanUtils.copyProperties(child, entity);
			childrenRepo.save(entity);
		});
		return children.get(0).getCaseNo();
	}

	
	@Override
	public DcSummeryReport showDcSummery(Integer caseNo) {
		DcIncomeEntity incomeEntity = incomeRepo.findByCaseNo(caseNo);
		System.out.println("------------------------"+incomeEntity);
		DcEducationEntity educationEntity = eduRepo.findByCaseNo(caseNo);
		System.out.println("------------------------"+educationEntity);
		List<DcChildrenEntity> childrenEntity = childrenRepo.findByCaseNo(caseNo);
		System.out.println("------------------------"+childrenEntity);
		Optional<DcCaseEntity> optCaseEntity = caseRepo.findById(caseNo);
		System.out.println("------------------------"+optCaseEntity.get());
		String planName = null;
		
		Integer appId = null;
		if (optCaseEntity.isPresent()) {
			Integer planId = optCaseEntity.get().getPlanId();
			System.out.println("------------------------"+planId);
			appId = optCaseEntity.get().getAppId();
			
			Optional<PlanEntity> optPlanEntity = planRepo.findById(planId);
			if(optPlanEntity.isPresent()) {
			planName = optPlanEntity.get().getPlanName();
			}
		}

		Optional<CitizenAppRegistartionEntity> optionalCitizenEntity = citizenAppRepo.findById(appId);
		CitizenAppRegistartionEntity citizenEntity = null;
		if (optionalCitizenEntity.isPresent()) {
			citizenEntity=optionalCitizenEntity.get();
		}
		
		IncomeInputs incomeInputs = new IncomeInputs();
		BeanUtils.copyProperties(incomeEntity, incomeInputs);
		EducationInputs eduInputs = new EducationInputs();
		BeanUtils.copyProperties(educationEntity, eduInputs);
		List<ChildInputs> childInputs = new ArrayList<>();
		childrenEntity.forEach(child -> {
			ChildInputs ch = new ChildInputs();
			BeanUtils.copyProperties(child, ch);
			childInputs.add(ch);
		});
		CitizenAppRegistrationInputs citizenInputs = new CitizenAppRegistrationInputs();
		BeanUtils.copyProperties(citizenEntity, citizenInputs);
		System.out.println("------------------------"+citizenEntity);
		DcSummeryReport report = new DcSummeryReport();
		report.setIncomeDetails(incomeInputs);
		report.setEducationDetails(eduInputs);
		report.setChildDetails(childInputs);
		report.setPlanName(planName);
		report.setCitizenAppRegDetails(citizenInputs);
		return report;

	}

}
