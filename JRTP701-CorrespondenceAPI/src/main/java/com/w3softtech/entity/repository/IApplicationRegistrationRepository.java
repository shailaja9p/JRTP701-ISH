package com.w3softtech.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3softtech.entity.CitizenAppRegistartionEntity;

public interface IApplicationRegistrationRepository extends JpaRepository<CitizenAppRegistartionEntity, Integer> {

}
