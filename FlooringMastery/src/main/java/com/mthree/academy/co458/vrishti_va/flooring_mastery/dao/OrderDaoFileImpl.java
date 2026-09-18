package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoFileImpl implements OrderDao {

    private Map<LocalDate, Map<Integer, Order>> ordersMap;

    public OrderDaoFileImpl() {
        this.ordersMap = new HashMap<>();
    }

    @Override
    public Order addOrder(LocalDate orderDate, Order order) {

        //Get or create in-memory map for orders on date
        Map<Integer, Order> ordersOnDateMap = ordersMap.computeIfAbsent(orderDate, key -> new HashMap<>());

        //Add order
        Order previousOrder = ordersOnDateMap.put(order.getOrderNumber(), order);

        return previousOrder;
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNumber, Order editedOrder) {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = ordersMap.get(orderDate);

        //Replace with edited order and return the original order, or null if the original didn't exist.
        if (ordersOnDateMap != null)
            return ordersOnDateMap.replace(orderNumber, editedOrder);
        else
            return null;
    }


    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = ordersMap.get(orderDate);

        //Remove and return the removed order, or null if it didn't exist.
        if (ordersOnDateMap != null)
            return ordersOnDateMap.remove(orderNumber);
        else
            return null;
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = ordersMap.get(orderDate);

        //Return order if found, otherwise null
        if (ordersOnDateMap != null)
            return ordersOnDateMap.get(orderNumber);
        else
            return null;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = ordersMap.get(date);

        //Return orders if found, otherwise empty list
        if (ordersOnDateMap != null)
            return new ArrayList<>(ordersOnDateMap.values());
        else
            return new ArrayList<>();
    }
}
