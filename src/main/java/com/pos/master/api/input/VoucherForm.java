package com.pos.master.api.input;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.pos.master.model.domain.entity.Voucher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoucherForm(@NotBlank(message = "Please enter customer name.") String customerName,@NotNull(message = "Please select category,") UUID categoryId,@NotNull(message = "Please enter fees.") BigDecimal fees, BigDecimal expenses, LocalDate date, String remark) {

	public VoucherForm(@NotBlank(message = "Please enter customer name.") String customerName,@NotNull(message = "Please select category,") UUID categoryId,@NotNull(message = "Please enter fees.") BigDecimal fees, BigDecimal expenses, LocalDate date, String remark) {
		this.customerName = customerName;
		this.categoryId = categoryId;
		this.fees = fees;
		this.expenses = expenses;
		this.date = date == null ? LocalDate.now() : date;
		this.remark = remark;
	}
	
	public Voucher toEntity() {
		var entity = new Voucher();
		entity.setCustomerName(customerName);
		entity.setFees(fees);
		entity.setExpenses(expenses);
		entity.setDate(date);
		entity.setRemark(remark);
		return entity;
	}

}
