package com.pos.master.api.output;

import com.pos.master.model.domain.entity.Category;
import com.pos.master.model.domain.entity.Category_;
import com.pos.master.model.domain.entity.Voucher;
import com.pos.master.model.domain.entity.Voucher_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Root;

public record CategoryVoucherDashboardData(String categoryName, long vouchers) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<CategoryVoucherDashboardData> cq,
			Root<Category> root, ListJoin<Category, Voucher> voucher) {

		cq.multiselect(
				root.get(Category_.name), 
				cb.count(voucher.get(Voucher_.id))
				);
		
		cq.groupBy(root.get(Category_.name));
	}

}
