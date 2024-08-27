package com.lifetree.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifetree.entity.PlanCategory;

public interface IPlanCategoryRepository extends JpaRepository<PlanCategory, Integer> {

}