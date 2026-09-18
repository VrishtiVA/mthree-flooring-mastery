package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.OrderDao;

import java.math.BigDecimal;

/**
 * This is the interface for the main/primary service component that
 * will be available in the Service layer.
 */
public interface MainService {

    public BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot);

    public BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot);

    public BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate);

    public BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax);

}
