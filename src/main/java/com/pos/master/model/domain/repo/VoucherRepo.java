package com.pos.master.model.domain.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.pos.master.api.output.VoucherInfo;
import com.pos.master.model.domain.BaseRepository;
import com.pos.master.model.domain.entity.Voucher;

public interface VoucherRepo extends BaseRepository<Voucher, UUID>{

	List<VoucherInfo> findByDate(LocalDate date);
	
}
