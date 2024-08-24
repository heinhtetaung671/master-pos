package com.pos.master.api.output;

import java.util.UUID;

import com.pos.master.model.domain.entity.Category;

public record CategorySelectData(UUID id, String name) {

	public static CategorySelectData from(Category entity) {
		return new CategorySelectData(entity.getId(), entity.getName());
	}
	
}
