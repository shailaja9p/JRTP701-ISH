package com.w3softtech.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.w3softtech.bindings.EligibilityDetailsOutput;
import com.w3softtech.entity.CitizenAppRegistartionEntity;
import com.w3softtech.entity.CoTriggersEntity;
import com.w3softtech.entity.DcCaseEntity;
import com.w3softtech.entity.DcChildrenEntity;
import com.w3softtech.entity.DcEducationEntity;
import com.w3softtech.entity.DcIncomeEntity;
import com.w3softtech.entity.EligibilityDetailsEntity;
import com.w3softtech.entity.PlanEntity;
import com.w3softtech.repository.IApplicationRegistrationRepository;
import com.w3softtech.repository.ICOTriggerRepository;
import com.w3softtech.repository.IDcCaseRepository;
import com.w3softtech.repository.IDcChildrenRepository;
import com.w3softtech.repository.IDcEducationRepository;
import com.w3softtech.repository.IDcIncomeRepository;
import com.w3softtech.repository.IEligibilityDetermineRepository;
import com.w3softtech.repository.IPlanRepository;

@Service
public class EligibilityDeterminationMgmtServiceImpl implements IEligibilityDeterminationMgmtService {

	@Autowired
	private IDcCaseRepository dcCaseRepo;
	@Autowired
	private IPlanRepository planRepo;
	@Autowired
	private IEligibilityDetermineRepository eligibilityRepo;
	@Autowired
	private IDcIncomeRepository incomeRepo;
	@Autowired
	private IDcChildrenRepository childRepo;
	@Autowired
	private IApplicationRegistrationRepository citizenRepo;
	@Autowired
	private IDcEducationRepository educationRepo;
	@Autowired
	private IEligibilityDetermineRepository elgRepo;
	@Autowired
	private ICOTriggerRepository triggerRepo;
	@Override
	public EligibilityDetailsOutput determineEligibility(Integer caseNo) {

		Integer appId = null;
		Integer planId = null;

		Optional<DcCaseEntity> optCaseEntity = dcCaseRepo.findById(caseNo);
		if (optCaseEntity.isPresent()) {
			DcCaseEntity caseEntity = optCaseEntity.get();
			appId = caseEntity.getAppId();
			planId = caseEntity.getPlanId();
		}
		String planName = null;
		Optional<PlanEntity> optPlanEntity = planRepo.findById(planId);
		if (optPlanEntity.isPresent()) {
			planName = optPlanEntity.get().getPlanName();
		}
		int citizenAge = 0;
		String citizenName=null;
		Optional<CitizenAppRegistartionEntity> optCitizenEntity = citizenRepo.findById(appId);
		if (optCitizenEntity.isPresent()) {
			CitizenAppRegistartionEntity citizenEntity = optCitizenEntity.get();
			LocalDate dob = citizenEntity.getDob();
			citizenName=citizenEntity.getFullName();
			citizenAge = Period.between(dob, LocalDate.now()).getYears();
		}
		//call helper method to plan conditions
		EligibilityDetailsOutput elgOutput = applyPlanConditions(caseNo, planName, citizenAge);
		elgOutput.setHolderName(citizenName);
		// save Eligibility entity object
		EligibilityDetailsEntity elgEntity = new EligibilityDetailsEntity();
		BeanUtils.copyProperties(elgOutput, elgEntity);
		elgRepo.save(elgEntity);
		
		// save COTrigger object
		CoTriggersEntity triggerEntity = new CoTriggersEntity();
		triggerEntity.setCaseNo(caseNo);
		triggerEntity.setTriggerStatus("pending");
		triggerRepo.save(triggerEntity);
		
		return elgOutput;
	}

	private EligibilityDetailsOutput applyPlanConditions(Integer caseNo, String planName, int citizenAge) {

		EligibilityDetailsOutput elgOutput = new EligibilityDetailsOutput();
		elgOutput.setPlanName(planName);

		DcIncomeEntity incomeEntity = incomeRepo.findByCaseNo(caseNo);
		double empIncome = incomeEntity.getEmpIncome();
		double propertyIncome = incomeEntity.getPropertyIncome();
System.out.println("======================"+planName);
		if (planName.equalsIgnoreCase("SNAP")) {
			if (empIncome <= 300) {
				elgOutput.setPlanStatus("Approved");
				elgOutput.setBenifitAmt(200.0);
			} else {
				elgOutput.setPlanStatus("Denied");
				elgOutput.setDenialReason("SNAP rules are not satisfied");
			}
		} else if (planName.equalsIgnoreCase("CCAP")) {
			boolean kidsCountCnd = false;
			boolean kidsAgeCnd = true;

			List<DcChildrenEntity> childEntity = childRepo.findByCaseNo(caseNo);
			if (!childEntity.isEmpty()) {
				kidsCountCnd = true;
				for (DcChildrenEntity child : childEntity) {
					// LocalDate childDob = childEntity.get(0).getChildDob();
					int kidAge = Period.between(child.getChildDob(), LocalDate.now()).getYears();
					if (kidAge > 16) {
						kidsAgeCnd = false;
						break;
					}
				}
			}
			if (empIncome <= 300 && kidsCountCnd && kidsAgeCnd) {
				elgOutput.setPlanStatus("Approved");
				elgOutput.setBenifitAmt(300.0);
			} else {
				elgOutput.setPlanStatus("Denied");
				elgOutput.setDenialReason("CCAP rules are not satisfied");
			}
		} else if (planName.equalsIgnoreCase("MEDCARE")) {
			if (citizenAge >= 65) {
				elgOutput.setPlanStatus("Approved");
				elgOutput.setBenifitAmt(350.0);
			} else {
				elgOutput.setPlanStatus("Denied");
				elgOutput.setDenialReason("Denied MEDCARE rules rae not satisfied");
			}
		} else if (planName.equalsIgnoreCase("MEDAID")) {
			if (empIncome <= 300 && propertyIncome == 0) {
				elgOutput.setPlanStatus("Approved");
				elgOutput.setBenifitAmt(200.0);
			} else {
				elgOutput.setPlanStatus("Denied");
				elgOutput.setDenialReason("MEDAID rules are not satisfied");
			}
		} else if (planName.equalsIgnoreCase("CAJW")) {
			DcEducationEntity educationEntity = educationRepo.findByCaseNo(caseNo);
			Integer passOutYear = educationEntity.getPassOutYear();
			if (empIncome == 0 && passOutYear < LocalDate.now().getYear()) {
				elgOutput.setPlanStatus("Approved");
				elgOutput.setBenifitAmt(300.0);
			} else {
				elgOutput.setPlanStatus("Denied");
				elgOutput.setDenialReason("CAJW rules are not satisfied");
			}
		} else if (planName.equalsIgnoreCase("QHP")) {
			if (citizenAge > 1) {
				elgOutput.setPlanStatus("Approved");
			} else {
				elgOutput.setPlanStatus("Denied");
				elgOutput.setDenialReason("QHP rules are not satisfied");
			}
		}
		if (elgOutput.getPlanStatus().equalsIgnoreCase("Approved")) {
			// set common properties for Elg output object
			elgOutput.setPlanStartDate(LocalDate.now());
			elgOutput.setPlanEndDate(LocalDate.now().plusYears(2));
		}
		return elgOutput;
	}
}
