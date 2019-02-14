package com.metacube.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 15-Sep-2018
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7139476234139282160L;

	@Column(name = "name")
	private String name;
	
	

	// To be removed
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }
	
	

}