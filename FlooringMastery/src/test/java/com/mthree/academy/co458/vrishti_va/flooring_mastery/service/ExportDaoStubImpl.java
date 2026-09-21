package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.ExportDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.PersistenceException;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.util.List;

public class ExportDaoStubImpl implements ExportDao {

    @Override
    public void exportActiveOrders(List<Order> activeOrders) throws PersistenceException {
        return;
    }

}
