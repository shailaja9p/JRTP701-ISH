package com.lifetree.bindings;

import lombok.Data;

@Data
public class PlanCategoryData {
	private Integer categoryId;
	private String categoryName;
	private String activeSW = "active";
}
