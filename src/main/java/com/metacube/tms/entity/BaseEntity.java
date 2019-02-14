package com.metacube.tms.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.metacube.tms.config.Auditable;

/**
 * @author Anurag Kumawat<anurag.kumawat@metacube.com>
 * @since 03-Nov-2018
 */

@MappedSuperclass
public class BaseEntity extends Auditable<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    private Long id; // will be set when persisting
	
	private boolean isActive = true;

	
	// TODO : Need to remove getter & setters

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
