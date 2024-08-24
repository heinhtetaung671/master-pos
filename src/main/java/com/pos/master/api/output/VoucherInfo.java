package com.pos.master.api.output;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.pos.master.model.domain.entity.Voucher;

public record VoucherInfo(String customerName, String categoryName, BigDecimal fees, BigDecimal expenses, LocalDate date, String remark) {

	public static VoucherInfo from(Voucher entity) {
		return new VoucherInfo(entity.getCustomerName(), entity.getCategory().getName(), entity.getFees(), entity.getExpenses(), entity.getDate(), entity.getRemark());
	}

}
