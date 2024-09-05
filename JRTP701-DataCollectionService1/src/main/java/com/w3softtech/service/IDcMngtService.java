package com.w3softtech.service;

import java.util.List;

import com.w3softtech.bindings.ChildInputs;
import com.w3softtech.bindings.DcSummeryReport;
import com.w3softtech.bindings.EducationInputs;
import com.w3softtech.bindings.IncomeInputs;
import com.w3softtech.bindings.PlanSelectionInputs;
import com.w3softtech.entity.DcIncomeEntity;

public interface IDcMngtService {

	Integer genearteCaseNumber(Integer appId);
	List<String> showAllPlanNames();
	Integer savePlanSelection(PlanSelectionInputs plan);
	Integer saveIncomeDetails(IncomeInputs income);
	Integer saveEducationDetails(EducationInputs education);
	Integer saveChildrenDetails(List<ChildInputs> children);
	DcSummeryReport showDcSummery(Integer caseNo);
	DcIncomeEntity showDcIncomeEntity(Integer caseNo);
}
