package com.pos.master.api.output;

import com.pos.master.model.domain.entity.Category;
import com.pos.master.model.domain.entity.Category_;
import com.pos.master.model.domain.entity.Voucher_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record CategoryInfo(String name, String description, long vouchers) {

	public static void select(CriteriaBuilder cb, CriteriaQuery<CategoryInfo> cq, Root<Category> root) {
		var vouchers = root.join(Category_.vouchers, JoinType.LEFT);
		
		cq.multiselect(root.get(Category_.name), root.get(Category_.description), cb.count(vouchers.get(Voucher_.id)));
		
		cq.groupBy(root.get(Category_.name), root.get(Category_.description));
	}

	public static CategoryInfo from(Category entity) {
		return new CategoryInfo(entity.getName(), entity.getDescription(), entity.getVouchers() == null ? 0 : entity.getVouchers().size());
	}

}
