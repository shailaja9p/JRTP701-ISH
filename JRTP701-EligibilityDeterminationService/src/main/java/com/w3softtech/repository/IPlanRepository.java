package com.w3softtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3softtech.entity.PlanEntity;

public interface IPlanRepository extends JpaRepository<PlanEntity, Integer>{

}
