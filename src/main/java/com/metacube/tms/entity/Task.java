/**
 * 
 */
package com.metacube.tms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "task")
public class Task extends BaseEntity {
	
	
	@Column(name = "description")
	private String desc;
	
	@Column(name = "target_date")
	private Date targetDate;
	
	@Column(name = "is_done")
	private boolean isDone;
	
	// To be removed

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Date getTargetDate() {
    return targetDate;
  }

  public void setTargetDate(Date targetDate) {
    this.targetDate = targetDate;
  }

  public boolean isDone() {
    return isDone;
  }

  public void setDone(boolean isDone) {
    this.isDone = isDone;
  }
	
}