package com.pos.master.api.output;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.pos.master.model.domain.entity.Voucher;
import com.pos.master.model.domain.entity.Voucher_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record DailyVoucherInfo(LocalDate date, BigDecimal fees, BigDecimal expenses, BigDecimal netProfit, long vouchers) {

	public DailyVoucherInfo(LocalDate date, BigDecimal fees, BigDecimal expenses, long vouchers) {
		this(date, fees, expenses, fees.subtract(expenses), vouchers);
	}
	
	public static void select(CriteriaBuilder cb, CriteriaQuery<DailyVoucherInfo> cq, Root<Voucher> root) {
		cq.multiselect(
				root.get(Voucher_.date), 
				cb.sum(root.get(Voucher_.fees)), 
				cb.sum(root.get(Voucher_.expenses)),
				cb.count(root.get(Voucher_.id))
				);
		
		cq.groupBy(root.get(Voucher_.date));
	}

}
