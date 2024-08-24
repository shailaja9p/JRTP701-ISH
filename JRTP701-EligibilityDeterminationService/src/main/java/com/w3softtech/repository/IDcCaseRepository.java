package com.w3softtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3softtech.entity.DcCaseEntity;

public interface IDcCaseRepository extends JpaRepository<DcCaseEntity, Integer> {
}
