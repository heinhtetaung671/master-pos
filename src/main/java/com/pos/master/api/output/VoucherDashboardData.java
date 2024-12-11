package com.pos.master.api.output;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.pos.master.model.domain.entity.Voucher;
import com.pos.master.model.domain.entity.Voucher_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record VoucherDashboardData(String label, BigDecimal fees, BigDecimal expenses) {

	public VoucherDashboardData(LocalDate date, BigDecimal fees, BigDecimal expenses) {
		this(date.toString(), fees, expenses);
	}
	
	public static void select(CriteriaBuilder cb, CriteriaQuery<VoucherDashboardData> cq, Root<Voucher> root, String dateFormat) {
		cq.multiselect(
				cb.function("TO_CHAR", String.class, root.get(Voucher_.date), cb.literal(dateFormat)),
				cb.coalesce(cb.sum(root.get(Voucher_.fees)), 0),
				cb.coalesce(cb.sum(root.get(Voucher_.expenses)), 0)
				);
		
		cq.groupBy(root.get(Voucher_.date));
	}


}
