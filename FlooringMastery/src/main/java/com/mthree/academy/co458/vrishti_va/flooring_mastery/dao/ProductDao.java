package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {

    /**
     * Get all the current product information.
     * @return A list of Product objects
     */
    public List<Product> getAllProducts();

    /**
     * Get all the current product information.
     * @return A map of Product objects, keyed by product type.
     */
    public Map<String, Product> getAllProductsIndexedByProductType();

}
