package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {

    public int getNextOrderNumber();

    /**
     * Add an order and return the previous order stored under the order number.
     * @param orderDate The order date.
     * @param order The order to add.
     * @return The previous order stored under the order number.
     */
    public Order addOrder(LocalDate orderDate, Order order) throws PersistenceException;

    /**
     * Edit a specific order, identified by order date and order number,
     * by updating it with an edited order.
     * @param orderDate The order date.
     * @param orderNumber The order number.
     * @param editedOrder The edited order.
     * @return The original order.
     * @throws NoSuchOrderException If there order to edit didn't exist.
     */
    public Order editOrder(LocalDate orderDate, int orderNumber, Order editedOrder) throws NoSuchOrderException, PersistenceException;

    /**
     * Remove a specific order, identified by order date and order number.
     * @param orderDate The order date.
     * @param orderNumber The order number.
     * @return The removed order.
     * @throws NoSuchOrderException If the order to remove didn't exist.
     */
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException, PersistenceException;

    /**
     * Get a specific order, identified by order date and order number.
     * @param orderDate The order date.
     * @param orderNumber The order number.
     * @return The requested order, or null if it doesn't exist.
     * @throws NoSuchOrderException If the order to get didn't exist
     */
    public Order getOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException;

    /**
     * Get all orders with the same order date.
     * @param date The order date.
     * @return A list of orders with the same order date, never null.
     */
    public List<Order> getOrdersByDate(LocalDate date);

    /**
     * Get all active orders (orders for today and the future).
     * @return A list of all active orders.
     */
    public List<Order> getAllActiveOrders();

}
