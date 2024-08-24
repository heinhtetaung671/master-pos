package com.pos.master.model.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Voucher {

	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(nullable = false)
	private String customerName;
	
	@Column(nullable = false)
	private BigDecimal fees;
	
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private BigDecimal expenses;
	
	private LocalDate date;
	private String remark;
	
	@ManyToOne(optional = false)
	private Category category;
	
	@CreatedDate
	private LocalDateTime createAt;
	@LastModifiedDate
	private LocalDateTime updateAt;
	private String createBy;
	private String updateBy;
	
}
