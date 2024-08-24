package com.pos.master.api;

import java.time.Month;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.master.api.input.CategoryVoucherDashboardSearch;
import com.pos.master.api.output.CategoryVoucherDashboardData;
import com.pos.master.api.output.VoucherDashboardMonthlyData;
import com.pos.master.api.output.VoucherDashboardYearlyData;
import com.pos.master.model.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/dashboard")
@RequiredArgsConstructor
public class DashboardApi {
	
	private final DashboardService service;
	
	@GetMapping("load-business-years")
	List<Integer> loadBusinessYears() {
		return null;
	}

	@GetMapping("category-voucher/monthly")
	List<CategoryVoucherDashboardData> searchCategoryVoucherMonthlyDate(CategoryVoucherDashboardSearch search) {
		return service.searchCategoryVoucherMonthlyData(search);
	}
	
	@GetMapping("category-voucher/yearly/{year}")
	List<CategoryVoucherDashboardData> searchCategoryVoucherYearlyData(@PathVariable int year) {
		return service.searchCategoryVoucherYearlyDate(year);
	}
	
	@GetMapping("voucher/monthly")
	List<VoucherDashboardMonthlyData> searchVoucherMonthlyData(int year, Month month) {
		return service.searchVoucherMonthlyData(year, month);
	}
	
	List<VoucherDashboardYearlyData> searchVoucherYearlyData(@PathVariable int year) {
		return service.searchVoucherYearlyData(year);
	}
}
