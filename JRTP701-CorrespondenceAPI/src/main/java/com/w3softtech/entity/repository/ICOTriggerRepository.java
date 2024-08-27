package com.w3softtech.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3softtech.entity.CoTriggersEntity;

public interface ICOTriggerRepository extends JpaRepository<CoTriggersEntity, Integer> {
	public List<CoTriggersEntity> findByTriggerStatus(String status);
}
