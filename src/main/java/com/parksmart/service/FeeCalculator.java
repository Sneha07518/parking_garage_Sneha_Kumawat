package com.parksmart.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FeeCalculator {
    private FeeCalculator() {
    }

    public static Breakdown calculate(long durationMinutes, BigDecimal firstHourRate,
                                      BigDecimal extraHourRate, BigDecimal dailyCap) {
        long hours = Math.max(1, (durationMinutes + 59) / 60);
        long fullDays = hours / 24;
        long remainingHours = hours % 24;
        BigDecimal remainder = BigDecimal.ZERO;
        if (remainingHours > 0) {
            remainder = firstHourRate.add(extraHourRate.multiply(BigDecimal.valueOf(remainingHours - 1L)))
                    .min(dailyCap);
        }
        BigDecimal total = dailyCap.multiply(BigDecimal.valueOf(fullDays)).add(remainder);
        return new Breakdown(durationMinutes, hours, fullDays, remainingHours, remainder,
                total.setScale(2, RoundingMode.HALF_UP));
    }

    public record Breakdown(long durationMinutes, long hoursBilled, long fullDays,
                            long remainingHours, BigDecimal remainderFee, BigDecimal totalFee) {
    }
}