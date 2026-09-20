package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.*;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MainServiceImpl implements MainService {

    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;
    private ExportDao exportDao;

        public MainServiceImpl(
            OrderDao orderDao,
            ProductDao productDao,
            TaxDao taxDao
//            ExportDao exportDao
    ) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
//        this.exportDao = exportDao;
    }

    /* ----- Calculation Methods ----- */

    private BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) {
        return area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) {
        return area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) {
        return (materialCost.add(laborCost))
                .multiply(taxRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax) {
        return materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public Order calculateOrderCosts(Order order) {

        //Perform calculations
        BigDecimal materialCost = calculateMaterialCost(order.getArea(), order.getCostPerSquareFoot());
        BigDecimal laborCost = calculateLaborCost(order.getArea(), order.getLaborCostPerSquareFoot());
        BigDecimal tax = calculateTax(materialCost, laborCost, order.getTaxRate());
        BigDecimal total = calculateTotal(materialCost, laborCost, tax);

        //Apply calculations
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);

        //Return order with calculations set
        return order;
    }

    /* ----- Tax & Product Methods ----- */

    @Override
    public List<Tax> getAllTaxes() {
        return taxDao.getAllTaxes();
    }

    @Override
    public Map<String, Tax> getAllTaxesIndexedByState() {
        return taxDao.getAllTaxesIndexedByState();
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public Map<String, Product> getAllProductsIndexedByProductType() {
        return productDao.getAllProductsIndexedByProductType();
    }

    /* ----- Order Methods ----- */

    @Override
    public List<Order> getOrdersByDate(LocalDate orderDate) {
        return orderDao.getOrdersByDate(orderDate);
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException {
        return orderDao.getOrder(orderDate, orderNumber);
    }

    /**
     * Add an order to the system.
     * @param orderDetails The order details of the order to add.
     * @return The newly generated order number where the order was added.
     *
     * @implNote
     * Since it is intended to keep the Order Number field read-only,
     * this method uses the Order constructor that uses a generated order number,
     * and populated using the details provided in an Order object treated solely as a DTO.
     */
    @Override
    public int addOrder(Order orderDetails) {

        //Generate order number and populate new order object - see implNote
        Order newOrder = new Order(orderDetails, orderDao.getNextOrderNumber());

        //Add order
        orderDao.addOrder(newOrder.getOrderDate(), newOrder);

        //Return newly generated order number
        return newOrder.getOrderNumber();
    }

    @Override
    public void editOrder(LocalDate orderDate, Order editedOrder) throws NoSuchOrderException {
        orderDao.editOrder(orderDate, editedOrder.getOrderNumber(), editedOrder);
    }

    @Override
    public void removeOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException {
        orderDao.removeOrder(orderDate, orderNumber);
    }

}
