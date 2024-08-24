package com.pos.master.model.utils;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

public class FindOneUtils {

	public static <T> T get(Optional<T> optional) {
		return optional.orElseThrow(() -> new EntityNotFoundException("Entit Not Found"));
	}	
	
}
