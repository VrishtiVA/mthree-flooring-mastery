package com.mthree.academy.co458.vrishti_va.flooring_mastery.dto;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SampleOrder {

    public static Order getSampleOrder1() {

        //Build valid sample order object
        Order order = new Order();
        order.setOrderDate(LocalDate.parse("06012013", DateTimeFormatter.ofPattern("MMddyyyy")));
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.21"));
        order.setTotal(new BigDecimal("2381.06"));

        //Return sample object
        return order;
    }

    public static Order getSampleOrder2() {

        //Build valid sample order object
        Order order = new Order();
        order.setOrderDate(LocalDate.parse("06022013", DateTimeFormatter.ofPattern("MMddyyyy")));
        order.setCustomerName("Doctor Who");
        order.setState("WA");
        order.setTaxRate(new BigDecimal("9.25"));
        order.setProductType("Wood");
        order.setArea(new BigDecimal("243.00"));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(new BigDecimal("1251.45"));
        order.setLaborCost(new BigDecimal("1154.25"));
        order.setTax(new BigDecimal("216.51"));
        order.setTotal(new BigDecimal("2622.21"));

        //Return sample object
        return order;
    }

    public static Order getSampleOrder3() {

        //Build valid sample order object
        Order order = new Order();
        order.setOrderDate(LocalDate.parse("06022013", DateTimeFormatter.ofPattern("MMddyyyy")));
        order.setCustomerName("Albert Einstein");
        order.setState("KY");
        order.setTaxRate(new BigDecimal("6.00"));
        order.setProductType("Carpet");
        order.setArea(new BigDecimal("217.00"));
        order.setCostPerSquareFoot(new BigDecimal("2.25"));
        order.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        order.setMaterialCost(new BigDecimal("488.25"));
        order.setLaborCost(new BigDecimal("455.70"));
        order.setTax(new BigDecimal("56.64"));
        order.setTotal(new BigDecimal("1000.59"));

        //Return sample object
        return order;
    }

    public static Order getSampleOrder4() {

        //Build valid sample order object
        Order order = new Order();
        order.setOrderDate(LocalDate.now().plusDays(5));
        order.setCustomerName("John Doe");
        order.setState("CA");
        order.setTaxRate(BigDecimal.valueOf(25.00));
        order.setProductType("Wood");
        order.setArea(BigDecimal.valueOf(100));
        order.setCostPerSquareFoot(new BigDecimal("5.15"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setMaterialCost(new BigDecimal("515.00"));
        order.setLaborCost(new BigDecimal("475.00"));
        order.setTax(new BigDecimal("247.50"));
        order.setTotal(new BigDecimal("1237.50"));

        //Return sample object
        return order;
    }

}
