package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.util.List;

public interface ExportDao {

    public void exportActiveOrders(List<Order> activeOrders) throws PersistenceException;

}
