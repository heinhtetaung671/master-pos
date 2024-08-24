package com.pos.master.model.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.master.api.input.CategoryVoucherDashboardSearch;
import com.pos.master.api.output.CategoryVoucherDashboardData;
import com.pos.master.api.output.VoucherDashboardMonthlyData;
import com.pos.master.api.output.VoucherDashboardYearlyData;
import com.pos.master.model.domain.entity.Category;
import com.pos.master.model.domain.entity.Category_;
import com.pos.master.model.domain.entity.Voucher_;
import com.pos.master.model.domain.repo.CategoryRepo;
import com.pos.master.model.domain.repo.VoucherRepo;

import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

	private final CategoryRepo categoryRepo;
	private final VoucherRepo voucherRepo;
	
	public List<CategoryVoucherDashboardData> searchCategoryVoucherMonthlyData(CategoryVoucherDashboardSearch search) {
		var yearMonth = YearMonth.of(search.year(), search.month());
		var startDate = yearMonth.atDay(1);
		var endDate = yearMonth.atEndOfMonth();
		
		return searchCategoryDashboardDataBetween2Date(startDate, endDate);
	}

	public List<CategoryVoucherDashboardData> searchCategoryVoucherYearlyDate(int year) {
		var startDate = YearMonth.of(year, Month.JANUARY).atDay(1);
		var endDate = YearMonth.of(year, Month.DECEMBER).atEndOfMonth();
		
		
		return searchCategoryDashboardDataBetween2Date(startDate, endDate);
	}
	
	private List<CategoryVoucherDashboardData> searchCategoryDashboardDataBetween2Date(LocalDate start, LocalDate end) {
		return categoryRepo.search(cb -> {
			var cq = cb.createQuery(CategoryVoucherDashboardData.class);
			var root = cq.from(Category.class);
			var voucher = root.join(Category_.vouchers, JoinType.LEFT);
			
			CategoryVoucherDashboardData.select(cb, cq, root, voucher);
			cq.where(cb.between(voucher.get(Voucher_.date), start, end));
			return cq;
		});
	}

	public List<VoucherDashboardMonthlyData> searchVoucherMonthlyData(int year, Month month) {
		var yearMonth = YearMonth.of(year, month);
		var startDate = yearMonth.atDay(1);
		
		var dateList = startDate.
		
		return voucherRepo.search(cb -> {
			var cq = cb.createQuery(VoucherDashboardMonthlyData.class);
			
			return cq;
		});
	}

	public List<VoucherDashboardYearlyData> searchVoucherYearlyData(int year) {
		return null;
	}

}
