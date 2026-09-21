package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.NoSuchOrderException;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.OrderDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.PersistenceException;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleOrder;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoStubImpl implements OrderDao {

    private int lastOrderNumber;

    private Map<Integer, Order> allOrders;

    public OrderDaoStubImpl() {

        //Initialise
        allOrders = new HashMap<>();
        lastOrderNumber = 2;

        //Populate map
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order1 = new Order(SampleOrder.getSampleOrder2(), 1);
        Order order2 = new Order(SampleOrder.getSampleOrder3(), 2);
        order1.setOrderDate(orderDate);
        order2.setOrderDate(orderDate);
        allOrders.put(order1.getOrderNumber(), order1);
        allOrders.put(order2.getOrderNumber(), order2);
    }

    @Override
    public int getNextOrderNumber() {
        return ++lastOrderNumber;
    }

    @Override
    public Order addOrder(LocalDate orderDate, Order order) throws PersistenceException {
        return allOrders.get(order.getOrderNumber());
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNumber, Order editedOrder) throws NoSuchOrderException, PersistenceException {

        if (allOrders.get(orderNumber) == null) {
            throw new NoSuchOrderException("There is no such order to edit");
        } else {
            return allOrders.get(orderNumber);
        }
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException, PersistenceException {

        if (allOrders.get(orderNumber) == null) {
            throw new NoSuchOrderException("There is no such order to remove");
        } else {
            return allOrders.get(orderNumber);
        }
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException {

        if (allOrders.get(orderNumber) == null) {
            throw new NoSuchOrderException("There is no such order to get");
        } else {
            return allOrders.get(orderNumber);
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        return new ArrayList<>(allOrders.values());
    }

    @Override
    public List<Order> getAllActiveOrders() {
        return new ArrayList<>(allOrders.values());
    }
}
