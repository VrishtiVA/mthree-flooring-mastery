package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.util.List;

public interface ExportDao {

    /**
     * Export all active orders.
     * @param activeOrders The active orders to export.
     * @throws PersistenceException If unable to export active orders to file.
     */
    public void exportActiveOrders(List<Order> activeOrders) throws PersistenceException;

}
