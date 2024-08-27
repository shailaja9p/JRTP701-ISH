package com.w3softtech.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="CITIZEN_APPLICATION")
@NoArgsConstructor
@AllArgsConstructor
public class CitizenAppRegistartionEntity {

	@Id
	@SequenceGenerator(name = "citizenRegSeq",sequenceName = "app_id_seq", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(generator = "citizenRegSeq", strategy = GenerationType.SEQUENCE )
	private Integer appId;
	@Column(length = 30)
	private String fullName;
	@Column(length = 30)
	private String email;
	@Column(length = 1)
	private String gender;
	private Long phoneNo;
	private Long ssn;
	@Column(length = 30)
	private String stateName;
	private LocalDate dob;
	@Column(length = 30)
	private String createBy;
	@Column(length = 30)
	private String  updateBy;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate creationDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate updationDate;
}
