package com.w3softtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3softtech.entity.DcIncomeEntity;

public interface IDcIncomeRepository extends JpaRepository<DcIncomeEntity, Integer> {

	public DcIncomeEntity findByCaseNo(Integer caseNo);
}
