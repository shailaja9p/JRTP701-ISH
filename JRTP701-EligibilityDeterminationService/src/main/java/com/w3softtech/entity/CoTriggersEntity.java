package com.w3softtech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="CO_TRIGGERS")
public class CoTriggersEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer coTriggerId;
	private Integer caseNo;
	//whenever u want to insert content to BLOB column u should take property type as byte[] and top of it u should add @Lob
	//pdf means byte[]... eg:.. images may be there
	//for text document means char[] ... eg: pure notepad
	@Lob
	private byte[] coNoticePdf;
	@Column(length = 30)
	private String triggerStatus="pending";
	
}
