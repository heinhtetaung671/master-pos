package com.pos.master.api;

import java.time.LocalDate;
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

import com.pos.master.api.input.VoucherForm;
import com.pos.master.api.input.VoucherSearch;
import com.pos.master.api.output.DailyVoucherInfo;
import com.pos.master.api.output.VoucherInfo;
import com.pos.master.model.service.VoucherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/voucher")
@RequiredArgsConstructor
public class VoucherApi {

	private final VoucherService service;
	
	@GetMapping
	List<DailyVoucherInfo> search(VoucherSearch search, BindingResult result) {
		return service.search(search);
	}
	
	@GetMapping("find/{date}")
	List<VoucherInfo> findByDate(@PathVariable LocalDate date){
		return service.findByDate(date);
	}
	
	@PostMapping
	VoucherInfo create(@RequestBody @Validated VoucherForm form, BindingResult result) {
		return service.create(form);
	}
	
	@PutMapping("{id}")
	VoucherInfo update(@PathVariable UUID id, @RequestBody @Validated VoucherForm form, BindingResult result) {
		return service.update(id, form);
	}
	
}
