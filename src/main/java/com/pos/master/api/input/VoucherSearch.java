package com.pos.master.api.input;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.pos.master.api.output.DailyVoucherInfo;
import com.pos.master.model.domain.entity.Category_;
import com.pos.master.model.domain.entity.Voucher;
import com.pos.master.model.domain.entity.Voucher_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record VoucherSearch(String keyword, UUID categoryId, java.time.LocalDate from, LocalDate to) {

	public Predicate[] where(CriteriaBuilder cb, CriteriaQuery<DailyVoucherInfo> cq, Root<Voucher> root) {
		var list = new ArrayList<Predicate>();
		
		if(null != categoryId) {
			list.add(cb.equal(root.get(Voucher_.category).get(Category_.id), categoryId));
		}
		
		if(StringUtils.hasLength(keyword)) {
			var concatedKeyword = keyword.toLowerCase().concat("%");
			list.add(cb.or(cb.like(cb.lower(root.get(Voucher_.customerName)), concatedKeyword), 
					cb.like(cb.lower(root.get(Voucher_.remark)), "%%%s%%".formatted(keyword.toLowerCase()))));
		}
		
		if(null != from) {
			list.add(cb.greaterThanOrEqualTo(root.get(Voucher_.date), from));
		}
		
		if(null != to) {
			list.add(cb.greaterThanOrEqualTo(root.get(Voucher_.date), to));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}

}
