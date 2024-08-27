package com.lifetree.entity;

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

@Data
@Entity
@Table(name="case-worker")
public class CaseWorkerEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer accountId;
	@Column(length = 30)
	private String fullName;
	@Column(length = 40)
	private String email;
	@Column(length = 40)
	private String password;
	@Column(length = 1)
	private String gender;
	private Integer SSN;
	private LocalDate dob;
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
