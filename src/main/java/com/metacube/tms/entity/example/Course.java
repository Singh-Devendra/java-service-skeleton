package com.metacube.tms.entity.example;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 17-Sep-2018
 */
@Data @AllArgsConstructor
public class Course {

	
	private String id;
	
	private String name;
	
	private String description;
	
	private List<String> steps;
	
	   
//To be removed

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

}