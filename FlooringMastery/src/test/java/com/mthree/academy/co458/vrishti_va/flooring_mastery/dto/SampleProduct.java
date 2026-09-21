package com.mthree.academy.co458.vrishti_va.flooring_mastery.dto;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;

import java.math.BigDecimal;

public class SampleProduct {

    public static Product getSampleProduct1() {
        return new Product(
            "Carpet",
            new BigDecimal("2.25"),
            new BigDecimal("2.10")
        );
    }

    public static Product getSampleProduct2() {
        return new Product(
            "Laminate",
            new BigDecimal("1.75"),
            new BigDecimal("2.10")
        );
    }

    public static Product getSampleProduct3() {
        return new Product(
            "Tile",
            new BigDecimal("3.50"),
            new BigDecimal("4.15")
        );
    }

    public static Product getSampleProduct4() {
        return new Product(
            "Wood",
            new BigDecimal("5.15"),
            new BigDecimal("4.75")
        );
    }

}