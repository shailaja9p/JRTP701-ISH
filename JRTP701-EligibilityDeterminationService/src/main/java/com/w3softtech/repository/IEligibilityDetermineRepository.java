package com.w3softtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3softtech.entity.EligibilityDetailsEntity;

public interface IEligibilityDetermineRepository extends JpaRepository<EligibilityDetailsEntity, Integer>{

}
