package com.pos.master.api;

import java.util.List;
import java.util.UUID;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.master.api.input.CategoryForm;
import com.pos.master.api.input.CategorySearch;
import com.pos.master.api.output.CategoryInfo;
import com.pos.master.api.output.CategorySelectData;
import com.pos.master.model.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryApi {

	private final CategoryService service;
	
	@PostMapping
	CategoryInfo create(@RequestBody @Validated CategoryForm form, BindingResult result) {
		return service.create(form);
	}
	
	@GetMapping
	List<CategoryInfo> search(@Validated CategorySearch search, BindingResult result) {
		return service.search(search);
	}
	
	@PutMapping("{id}")
	CategoryInfo update(@PathVariable UUID id, @Validated @RequestBody CategoryForm form, BindingResult result) {
		return service.update(id, form);
	}
	
	@GetMapping("load-data-for-select")
	List<CategorySelectData> loadDataForSelect() {
		return service.loadDataForSelect();
	}
	
	
	
}
