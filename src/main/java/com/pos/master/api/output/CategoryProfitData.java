package com.pos.master.api.output;

import java.math.BigDecimal;

import com.pos.master.model.domain.entity.Category;
import com.pos.master.model.domain.entity.Category_;
import com.pos.master.model.domain.entity.Voucher;
import com.pos.master.model.domain.entity.Voucher_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Root;

public record CategoryProfitData(String label, BigDecimal profit) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<CategoryProfitData> cq, Root<Category> root, ListJoin<Category, Voucher> vouchers) {
		cq.multiselect(
				root.get(Category_.name), 
				cb.diff(cb.coalesce(cb.sum(vouchers.get(Voucher_.fees)), 0), cb.coalesce(cb.sum(vouchers.get(Voucher_.expenses)), 0))
				);
		
		cq.groupBy(root.get(Category_.name));
	}

}
