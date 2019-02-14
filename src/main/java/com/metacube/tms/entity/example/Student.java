package com.metacube.tms.entity.example;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 17-Sep-2018
 */
@Data @AllArgsConstructor
public class Student {
	
	private String id;

	private String name;
	
	private String description;
	
	private List<Course> courses;
	
	// To be removed
	

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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
