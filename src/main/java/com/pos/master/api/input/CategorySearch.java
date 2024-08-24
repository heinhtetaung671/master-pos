package com.pos.master.api.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.pos.master.api.output.CategoryInfo;
import com.pos.master.model.domain.entity.Category;
import com.pos.master.model.domain.entity.Category_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record CategorySearch(String keyword) {

	public Predicate[] where(CriteriaBuilder cb, CriteriaQuery<CategoryInfo> cq, Root<Category> root) {
		var list = new ArrayList<Predicate>();

		if( keyword != null && StringUtils.hasLength(keyword)) {
			var concatedKeyword = keyword.toLowerCase().concat("%");
			
			list.add(cb.or(cb.like(cb.lower(root.get(Category_.name)), concatedKeyword), 
					cb.like(cb.lower(root.get(Category_.description)), concatedKeyword)));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}



}
