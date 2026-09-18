package com.mthree.academy.co458.vrishti_va.flooring_mastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;

    public Product() {}

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    /* ----- Getters ----- */
    public String getProductType() {return productType;}
    public BigDecimal getCostPerSquareFoot() {return costPerSquareFoot;}
    public BigDecimal getLaborCostPerSquareFoot() {return laborCostPerSquareFoot;}

    /* ----- Setters ----- */
    /* There are no setter methods for this class, as all fields are read-only. */

    @Override
    public String toString() {
        return "Product{" +
                "productType='" + productType + '\'' +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productType, product.productType) && Objects.equals(costPerSquareFoot, product.costPerSquareFoot) && Objects.equals(laborCostPerSquareFoot, product.laborCostPerSquareFoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, costPerSquareFoot, laborCostPerSquareFoot);
    }

}
