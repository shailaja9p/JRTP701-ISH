package com.w3softtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3softtech.entity.DcEducationEntity;
import com.w3softtech.entity.DcIncomeEntity;

public interface IDcEducationRepository extends JpaRepository<DcEducationEntity, Integer> {

public DcEducationEntity findByCaseNo(Integer caseNo);
}
