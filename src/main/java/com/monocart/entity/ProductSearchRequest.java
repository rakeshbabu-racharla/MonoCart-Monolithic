package com.monocart.entity;

public class ProductSearchRequest {

	private String name;
	private Integer categoryId;
	private String categoryName;
	
	public String getName() {
		return name;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	

}
