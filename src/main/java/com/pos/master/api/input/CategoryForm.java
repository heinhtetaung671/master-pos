package com.pos.master.api.input;

import com.pos.master.model.domain.entity.Category;

import jakarta.validation.constraints.NotBlank;

public record CategoryForm(@NotBlank(message = "Please enter category name.") String name, String description) {

	public Category toEntity() {
		var entity = new Category();
		entity.setName(name);
		entity.setDescription(description);
		return entity;
	}

}
