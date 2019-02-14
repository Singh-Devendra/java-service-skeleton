package com.metacube.tms.config;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yash Sharma<yash.sharma@metacube.com>
 * @since 21-Sep-2018
 */
@Data @NoArgsConstructor @AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

	@CreatedBy
	protected U createdBy;

	@CreatedDate
	@Temporal(TIMESTAMP)
	protected Date creationDate;

	@LastModifiedBy
	protected U lastModifiedBy;

	@LastModifiedDate
	@Temporal(TIMESTAMP)
	protected Date lastModifiedDate;

}
