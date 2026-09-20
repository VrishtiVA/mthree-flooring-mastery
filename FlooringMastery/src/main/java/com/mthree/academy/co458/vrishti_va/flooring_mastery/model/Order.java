package com.mthree.academy.co458.vrishti_va.flooring_mastery.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order {

    private int orderNumber;
    private LocalDate orderDate;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Order() {}

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Order(Order orderDetails, int orderNumber) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDetails.getOrderDate();
        this.customerName = orderDetails.getCustomerName();
        this.state = orderDetails.getState();
        this.taxRate = orderDetails.getTaxRate();
        this.productType = orderDetails.getProductType();
        this.area = orderDetails.getArea();
        this.costPerSquareFoot = orderDetails.getCostPerSquareFoot();
        this.laborCostPerSquareFoot = orderDetails.getLaborCostPerSquareFoot();
        this.materialCost = orderDetails.getMaterialCost();
        this.laborCost = orderDetails.getLaborCost();
        this.tax = orderDetails.getTax();
        this.total = orderDetails.getTotal();
    }

    /* ----- Getter Methods ----- */
    public int getOrderNumber() {return orderNumber;}
    public LocalDate getOrderDate() {return orderDate;}
    public String getCustomerName() {return customerName;}
    public String getState() {return state;}
    public BigDecimal getTaxRate() {return taxRate;}
    public String getProductType() {return productType;}
    public BigDecimal getArea() {return area;}
    public BigDecimal getCostPerSquareFoot() {return costPerSquareFoot;}
    public BigDecimal getLaborCostPerSquareFoot() {return laborCostPerSquareFoot;}
    public BigDecimal getMaterialCost() {return materialCost;}
    public BigDecimal getLaborCost() {return laborCost;}
    public BigDecimal getTax() {return tax;}
    public BigDecimal getTotal() {return total;}

    /* ----- Setter Methods ----- */
    public void setOrderDate(LocalDate orderDate) {this.orderDate = orderDate;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public void setState(String state) {this.state = state;}
    public void setTaxRate(BigDecimal taxRate) {this.taxRate = taxRate;}
    public void setProductType(String productType) {this.productType = productType;}
    public void setArea(BigDecimal area) {this.area = area;}
    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {this.costPerSquareFoot = costPerSquareFoot;}
    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {this.laborCostPerSquareFoot = laborCostPerSquareFoot;}
    public void setMaterialCost(BigDecimal materialCost) {this.materialCost = materialCost;}
    public void setLaborCost(BigDecimal laborCost) {this.laborCost = laborCost;}
    public void setTax(BigDecimal tax) {this.tax = tax;}
    public void setTotal(BigDecimal total) {this.total = total;}

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", orderDate=" + orderDate +
                ", customerName='" + customerName + '\'' +
                ", state='" + state + '\'' +
                ", taxRate=" + taxRate +
                ", productType='" + productType + '\'' +
                ", area=" + area +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                ", materialCost=" + materialCost +
                ", laborCost=" + laborCost +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber && Objects.equals(orderDate, order.orderDate) && Objects.equals(customerName, order.customerName) && Objects.equals(state, order.state) && Objects.equals(taxRate, order.taxRate) && Objects.equals(productType, order.productType) && Objects.equals(area, order.area) && Objects.equals(costPerSquareFoot, order.costPerSquareFoot) && Objects.equals(laborCostPerSquareFoot, order.laborCostPerSquareFoot) && Objects.equals(materialCost, order.materialCost) && Objects.equals(laborCost, order.laborCost) && Objects.equals(tax, order.tax) && Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, orderDate, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
    }

}
