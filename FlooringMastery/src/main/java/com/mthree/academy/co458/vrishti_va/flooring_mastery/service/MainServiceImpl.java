package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.ExportDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.OrderDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.ProductDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.TaxDao;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainServiceImpl implements MainService {

    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;
    private ExportDao exportDao;

    //    public MainServiceImpl(
//            OrderDao orderDao,
//            ProductDao productDao,
//            TaxDao taxDao,
//            ExportDao exportDao
//    ) {
//        this.orderDao = orderDao;
//        this.productDao = productDao;
//        this.taxDao = taxDao;
//        this.exportDao = exportDao;
//    }

    @Override
    public BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) {
        return area.multiply(costPerSquareFoot);
    }

    @Override
    public BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) {
        return area.multiply(laborCostPerSquareFoot);
    }

    @Override
    public BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) {
        return (materialCost.add(laborCost)).multiply(taxRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
    }

    @Override
    public BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax) {
        return materialCost.add(laborCost).add(tax);
    }

}
