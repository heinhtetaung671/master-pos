package com.pos.master.model.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.master.api.input.CategoryForm;
import com.pos.master.api.input.CategorySearch;
import com.pos.master.api.output.CategoryInfo;
import com.pos.master.api.output.CategorySelectData;
import com.pos.master.model.domain.entity.Category;
import com.pos.master.model.domain.entity.Category_;
import com.pos.master.model.domain.repo.CategoryRepo;
import com.pos.master.model.utils.FindOneUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

	private final CategoryRepo repo;
	
	public List<CategoryInfo> search(CategorySearch search) {
		return repo.search(cb -> {
			var cq = cb.createQuery(CategoryInfo.class);
			var root = cq.from(Category.class);
			CategoryInfo.select(cb, cq, root);
			cq.where(search.where(cb, cq, root));
			cq.orderBy(cb.asc(root.get(Category_.name)));
			return cq;
		});
	}
	
	@Transactional
	public CategoryInfo create(CategoryForm form) {
		return CategoryInfo.from(repo.save(form.toEntity()));
	}
	
	@Transactional
	public CategoryInfo update(UUID id, CategoryForm form) {
		var entity = FindOneUtils.get(repo.findById(id));
		entity.setName(form.name());
		entity.setDescription(form.description());
		return CategoryInfo.from(entity);
	}

	public List<CategorySelectData> loadDataForSelect() {
		return repo.findAll().stream().map(CategorySelectData::from).toList();
	}
}
