package com.w3softtech.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Table(name="plan_master")
@Entity
@Data
public class PlanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer planId;
	@Column(length = 30)
	private String planName;
	private LocalDate startDate;
	private LocalDate endDate;
	@Column(length = 30)
	private String description;
	@Column(length = 30)
	private String activeSw;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime creationDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updattionDate;
	@Column(length = 30)
	private String createdBy;
	@Column(length = 30)
	private String updatedBy;
}
