package com.mthree.academy.co458.vrishti_va.flooring_mastery.dto;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.math.BigDecimal;

public class SampleTax {

    public static Tax getSampleTax1() {
        return new Tax(
            "TX",
            "Texas",
            new BigDecimal("4.45")
        );
    }

    public static Tax getSampleTax2() {
        return new Tax(
            "WA",
            "Washington",
            new BigDecimal("9.25")
        );
    }

    public static Tax getSampleTax3() {
        return new Tax(
            "KY",
            "Kentucky",
            new BigDecimal("6.00")
        );
    }

    public static Tax getSampleTax4() {
        return new Tax(
            "CA",
            "Calfornia",
            new BigDecimal("25.00")
        );
    }

}