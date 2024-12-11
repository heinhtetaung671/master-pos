package com.pos.master.model.domain.repo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.pos.master.api.output.VoucherInfo;
import com.pos.master.model.domain.BaseRepository;
import com.pos.master.model.domain.entity.Voucher;

public interface VoucherRepo extends BaseRepository<Voucher, UUID>{

	List<VoucherInfo> findByDate(LocalDate date);
	
	@Query("SELECT COALESCE(SUM(v.expenses), 0) FROM Voucher v WHERE v.date = ?1")
	BigDecimal findSumExpenseByDate(LocalDate date);
	
	@Query("SELECT COALESCE(SUM(v.fees), 0) FROM Voucher v WHERE v.date = ?1")
	BigDecimal findSumFeesByDate(LocalDate date);

	@Query("SELECT COALESCE(SUM(v.fees), 0) FROM Voucher v WHERE v.date BETWEEN ?1 AND ?2")
	BigDecimal findSumFeesByDateBetween(LocalDate from, LocalDate to);

	@Query("SELECT COALESCE(SUM(v.expenses), 0) FROM Voucher v WHERE v.date BETWEEN ?1 AND ?2")
	BigDecimal findSumExpenseByDateBetween(LocalDate from, LocalDate to);
	
	@Query("SELECT COALESCE(SUM(v.fees), 0) - COALESCE(SUM(v.expenses), 0) FROM Voucher v WHERE v.date BETWEEN ?1 AND ?2")
	BigDecimal findProfitByDateBetween(LocalDate from, LocalDate to);
}
