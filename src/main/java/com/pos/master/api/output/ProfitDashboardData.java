package com.pos.master.api.output;

import com.pos.master.model.domain.entity.Voucher;
import com.pos.master.model.domain.entity.Voucher_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record ProfitDashboardData(String label, java.math.BigDecimal profit) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<ProfitDashboardData> cq, Root<Voucher> root,
			String dateFormat) {
		cq.multiselect(
				cb.function("TO_CHAR", String.class, root.get(Voucher_.date), cb.literal(dateFormat)),
				cb.diff(cb.coalesce(cb.sum(root.get(Voucher_.fees)), 0), cb.coalesce(cb.sum(root.get(Voucher_.expenses)), 0))
				);
		
		cq.groupBy(root.get(Voucher_.date));
	}

}
