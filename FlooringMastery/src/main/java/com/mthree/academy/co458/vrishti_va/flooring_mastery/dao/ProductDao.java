package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;

import java.util.List;

public interface ProductDao {

    /**
     * Get all the current product information.
     * @return A list of Product objects
     */
    public List<Product> getAllProducts();

}
