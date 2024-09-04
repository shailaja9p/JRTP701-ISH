package com.w3softtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w3softtech.entity.DcChildrenEntity;

public interface IDcChildrenRepository extends JpaRepository<DcChildrenEntity, Integer> {

	public List<DcChildrenEntity> findByCaseNo(Integer caseNo);
}
