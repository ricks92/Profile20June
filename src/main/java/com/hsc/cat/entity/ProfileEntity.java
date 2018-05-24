package com.hsc.cat.entity;

import javax.persistence.Entity;

@Entity
public class ProfileEntity {

	private int id;
	private String empId;
	private String project_role;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getProject_role() {
		return project_role;
	}
	public void setProject_role(String project_role) {
		this.project_role = project_role;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "ProfileEntity [id=" + id + ", empId=" + empId + ", project_role=" + project_role + ", description="
				+ description + "]";
	}
	
	
	
}
