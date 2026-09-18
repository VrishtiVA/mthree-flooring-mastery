package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This is the interface for the main/primary service component that
 * will be available in the Service layer.
 */
public interface MainService {

    /* ----- Business Logic: Calculations ----- */

    public BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot);

    public BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot);

    public BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate);

    public BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax);

    /* ----- DAO Interactions ----- */

    public List<Order> getOrdersByDate(LocalDate orderDate);

    public Order getOrder(LocalDate orderDate, int orderNumber);

    public void addOrder(LocalDate orderDate, Order order);

    public void editOrder(LocalDate orderDate, int orderNumber, Order editedOrder);

    public void removeOrder(LocalDate orderDate, int orderNumber);

}
