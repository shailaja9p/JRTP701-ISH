package com.lifetree.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifetree.entity.CaseWorkerEntity;

public interface ICaseworkerRepository extends JpaRepository<CaseWorkerEntity, Integer> {

}
