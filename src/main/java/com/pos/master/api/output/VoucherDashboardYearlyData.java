package com.pos.master.api.output;

import java.math.BigDecimal;
import java.time.Month;

public record VoucherDashboardYearlyData(Month month, BigDecimal fees, BigDecimal expenses) {

}
