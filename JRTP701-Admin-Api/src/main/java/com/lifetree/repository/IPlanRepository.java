package com.lifetree.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifetree.entity.PlanEntity;

public interface IPlanRepository extends JpaRepository<PlanEntity, Integer>{

}
