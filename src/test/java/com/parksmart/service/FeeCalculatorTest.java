package com.parksmart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class FeeCalculatorTest {
    private static final BigDecimal FIRST = new BigDecimal("50");
    private static final BigDecimal EXTRA = new BigDecimal("30");
    private static final BigDecimal CAP = new BigDecimal("300");

    @Test
    void calculatesRequiredDurations() {
        assertFee(1, 50, 1, 0, 1);
        assertFee(59, 50, 1, 0, 1);
        assertFee(60, 50, 1, 0, 1);
        assertFee(61, 80, 2, 0, 2);
        assertFee(300, 170, 5, 0, 5);
        assertFee(720, 300, 12, 0, 12);
        assertFee(1440, 300, 24, 1, 24);
        assertFee(1500, 350, 25, 1, 25);
        assertFee(3000, 680, 50, 2, 50);
    }

    private void assertFee(long minutes, int expectedFee, int expectedHours,
                           int expectedFullDays, int expectedReportedHours) {
        FeeCalculator.Breakdown result = FeeCalculator.calculate(minutes, FIRST, EXTRA, CAP);
        assertEquals(expectedHours, result.hoursBilled());
        assertEquals(expectedFullDays, result.fullDays());
        assertEquals(expectedReportedHours, result.fullDays() * 24 + result.remainingHours());
        assertEquals(new BigDecimal(expectedFee).setScale(2), result.totalFee());
    }
}