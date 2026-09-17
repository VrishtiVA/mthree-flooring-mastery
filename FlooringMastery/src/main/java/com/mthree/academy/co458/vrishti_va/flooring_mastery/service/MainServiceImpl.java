package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.ExportDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.OrderDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.ProductDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.TaxDao;

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

}
