package com.pos.master.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.master.api.input.CategoryVoucherDashboardSearch;
import com.pos.master.api.output.CategoryProfitData;
import com.pos.master.api.output.CategoryVoucherDashboardData;
import com.pos.master.api.output.ProfitDashboardData;
import com.pos.master.api.output.VoucherDashboardData;
import com.pos.master.model.domain.entity.Category;
import com.pos.master.model.domain.entity.Category_;
import com.pos.master.model.domain.entity.Voucher;
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
	
	private static final String MONTHLY_LABEL_FORMAT_PATTERN = "dd MON";
	private static final DateTimeFormatter MONTHLY_LABEL_FORMATTER = DateTimeFormatter.ofPattern("dd MMM");
	
	public List<CategoryVoucherDashboardData> searchCategoryVoucherMonthlyData(CategoryVoucherDashboardSearch search) {
		var yearMonth = YearMonth.of(search.year(), search.month());
		var startDate = yearMonth.atDay(1);
		var endDate = yearMonth.atEndOfMonth();
		
		return searchCategoryVoucherDataBetween2Date(startDate, endDate);
	}

	public List<CategoryVoucherDashboardData> searchCategoryVoucherYearlyDate(int year) {
		var startDate = YearMonth.of(year, Month.JANUARY).atDay(1);
		var endDate = YearMonth.of(year, Month.DECEMBER).atEndOfMonth();
		
		return searchCategoryVoucherDataBetween2Date(startDate, endDate);
	}
	
	private List<CategoryVoucherDashboardData> searchCategoryVoucherDataBetween2Date(LocalDate start, LocalDate end) {
		return categoryRepo.search(cb -> {
			var cq = cb.createQuery(CategoryVoucherDashboardData.class);
			var root = cq.from(Category.class);
			var voucher = root.join(Category_.vouchers, JoinType.LEFT);
			
			CategoryVoucherDashboardData.select(cb, cq, root, voucher);
			cq.where(cb.between(voucher.get(Voucher_.date), start, end));
			return cq;
		});
	}

	public List<VoucherDashboardData> searchVoucherMonthlyData(int year, Month month) {
		var yearMonth = YearMonth.of(year, month);
		var startDate = yearMonth.atDay(1);
		
		var dateList = startDate.datesUntil(yearMonth.atEndOfMonth().plusDays(1)).toArray(LocalDate[]::new);
		
		var result = new ArrayList<VoucherDashboardData>();
		
		for(var date: dateList) {
			
			var temp = voucherRepo.searchOne(cb -> {
				var cq = cb.createQuery(VoucherDashboardData.class);
				var root = cq.from(Voucher.class);
				VoucherDashboardData.select(cb, cq, root, MONTHLY_LABEL_FORMAT_PATTERN);
				cq.where(cb.equal(root.get(Voucher_.date), date));
				return cq;
			});
			
			result.add(temp == null ? new VoucherDashboardData(date.format(MONTHLY_LABEL_FORMATTER), BigDecimal.ZERO, BigDecimal.ZERO) : temp);
		}
		
		return result;
	}

	public List<VoucherDashboardData> searchVoucherYearlyData(int year) {
		
		var result = new ArrayList<VoucherDashboardData>();
		for(var month: Month.values()) {
			
			var yearMonth = YearMonth.of(year, month);
			var from = yearMonth.atDay(1);
			var to = yearMonth.atEndOfMonth();
			result.add(new VoucherDashboardData(
					month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
					voucherRepo.findSumFeesByDateBetween(from, to),
					voucherRepo.findSumExpenseByDateBetween(from, to)));
		}
		
		return result;
	}

	public List<ProfitDashboardData> searchProfitMonthlyData(int year, Month month) {
		var yearMonth = YearMonth.of(year, month);
		var startDate = yearMonth.atDay(1);
		
		var dateList = startDate.datesUntil(yearMonth.atEndOfMonth().plusDays(1)).toArray(LocalDate[]::new);
		
		var result = new ArrayList<ProfitDashboardData>();
		
		for(var date: dateList) {
			
			var temp = voucherRepo.searchOne(cb -> {
				var cq = cb.createQuery(ProfitDashboardData.class);
				var root = cq.from(Voucher.class);
				ProfitDashboardData.select(cb, cq, root, MONTHLY_LABEL_FORMAT_PATTERN);
				cq.where(cb.equal(root.get(Voucher_.date), date));
				return cq;
			});
			
			result.add(temp == null ? new ProfitDashboardData(date.format(MONTHLY_LABEL_FORMATTER), BigDecimal.ZERO) : temp);
		}
		
		return result;
	}

	public List<ProfitDashboardData> searchProfitYearlyData(int year) {
		var result = new ArrayList<ProfitDashboardData>();
		for(var month: Month.values()) {
			
			var yearMonth = YearMonth.of(year, month);
			var from = yearMonth.atDay(1);
			var to = yearMonth.atEndOfMonth();
			result.add(new ProfitDashboardData(
					month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
					voucherRepo.findProfitByDateBetween(from, to)));
		}
		
		return result;	
	}

	public List<CategoryProfitData> searchCategoryProfitMonthlyData(int year, Month month) {
		var yearMonth = YearMonth.of(year, month);
		var startDate = yearMonth.atDay(1);
		var endDate = yearMonth.atEndOfMonth();
		
		return searchCategoryProfitDataBetween2Date(startDate, endDate);
	}

	public List<CategoryProfitData> searchCategoryProfitYearlyData(int year) {
		var startDate = YearMonth.of(year, Month.JANUARY).atDay(1);
		var endDate = YearMonth.of(year, Month.DECEMBER).atEndOfMonth();
		
		return searchCategoryProfitDataBetween2Date(startDate, endDate);
	}
	
	private List<CategoryProfitData> searchCategoryProfitDataBetween2Date(LocalDate from, LocalDate to) {
		return categoryRepo.search(cb -> {
			var cq = cb.createQuery(CategoryProfitData.class);
			var root = cq.from(Category.class);
			var vouchers = root.join(Category_.vouchers, JoinType.LEFT);
			
			CategoryProfitData.select(cb, cq, root, vouchers);
			cq.where(cb.between(vouchers.get(Voucher_.date), from, to));
			return cq;
		});
	}

}
