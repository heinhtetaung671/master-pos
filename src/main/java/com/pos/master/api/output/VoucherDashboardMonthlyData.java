package com.pos.master.api.output;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VoucherDashboardMonthlyData(LocalDate month, BigDecimal fees, BigDecimal expenses) {

}
