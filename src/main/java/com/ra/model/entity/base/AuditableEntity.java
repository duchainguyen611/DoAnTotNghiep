package com.ra.model.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(EntityAuditListener.class)
@Getter
@Setter
public class AuditableEntity {
	private LocalDate createdDate;
	private LocalDate modifyDate;
	private String createBy;
	private String modifyBy;
}
