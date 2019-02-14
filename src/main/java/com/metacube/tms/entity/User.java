/**
 * 
 */
package com.metacube.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {
 
	private static final long serialVersionUID = 7660662926037739936L;

	@Column(name = "first_name")
    private String firstName;
	
	@Column(name = "last_name")
    private String lastName;
	
//	To be removed

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }
	
	
 
}