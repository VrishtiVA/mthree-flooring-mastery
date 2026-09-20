package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.NoSuchOrderException;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * This is the interface for the main/primary service component that
 * will be available in the Service layer.
 */
public interface MainService {

    /* ----- Business Logic: Calculations ----- */

    public Order calculateOrderCosts(Order order);

    /* ----- Product & Tax DAO Interactions ----- */

    public List<Tax> getAllTaxes();

    public Map<String, Tax> getAllTaxesIndexedByState();

    public List<Product> getAllProducts();

    public Map<String, Product> getAllProductsIndexedByProductType();

    /* ----- Order DAO Interactions ----- */

    public List<Order> getOrdersByDate(LocalDate orderDate);

    public Order getOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException;

    /**
     * Add an order to the system.
     * @param order The order to add.
     * @return The newly generated order number for the order.
     */
    public int addOrder(Order order);

    /**
     * Edit an order in the system
     * @param orderDate The order date of the order to edit.
     * @param editedOrder The edited copy of the order object.
     * @throws NoSuchOrderException If the order to edit didn't originally exist in the system.
     */
    public void editOrder(LocalDate orderDate, Order editedOrder) throws NoSuchOrderException;

    public void removeOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException;

}
