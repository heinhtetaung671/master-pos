package com.pos.master.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.master.api.input.VoucherForm;
import com.pos.master.api.input.VoucherSearch;
import com.pos.master.api.output.DailyVoucherInfo;
import com.pos.master.api.output.VoucherInfo;
import com.pos.master.model.domain.entity.Voucher;
import com.pos.master.model.domain.entity.Voucher_;
import com.pos.master.model.domain.repo.CategoryRepo;
import com.pos.master.model.domain.repo.VoucherRepo;
import com.pos.master.model.utils.FindOneUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoucherService {

	private final VoucherRepo repo;
	private final CategoryRepo categoryRepo;

	@Transactional
	public VoucherInfo create(VoucherForm form) {
		var entity = form.toEntity();
		entity.setCategory(FindOneUtils.get(categoryRepo.findById(form.categoryId())));
		return VoucherInfo.from(repo.save(entity));
	}

	@Transactional
	public VoucherInfo update(UUID id, VoucherForm form) {
		var entity = FindOneUtils.get(repo.findById(id));
		entity.setCustomerName(form.customerName());
		entity.setFees(form.fees());
		entity.setExpenses(form.expenses());
		entity.setDate(form.date());
		entity.setRemark(form.remark());
		return VoucherInfo.from(entity);
	}

	public List<DailyVoucherInfo> search(VoucherSearch search) {
		return repo.search(cb -> {
			var cq = cb.createQuery(DailyVoucherInfo.class);
			var root = cq.from(Voucher.class);
			DailyVoucherInfo.select(cb, cq, root);
			cq.where(search.where(cb, cq, root));
			cq.orderBy(cb.asc(root.get(Voucher_.date)));
			return cq;
		});
	}

	public List<VoucherInfo> findByDate(LocalDate date) {
		return repo.findByDate(date);
	}
	
	
	
}
