package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.ProductDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleProduct;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleTax;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDaoStubImpl implements ProductDao {

    private Map<String, Product> allProducts;

    public ProductDaoStubImpl() {

        allProducts = new HashMap<>();

        //Populate products map
        Product product = SampleProduct.getSampleProduct1();
        allProducts.put(product.getProductType(), product);
        product = SampleProduct.getSampleProduct2();
        allProducts.put(product.getProductType(), product);
        product = SampleProduct.getSampleProduct3();
        allProducts.put(product.getProductType(), product);
        product = SampleProduct.getSampleProduct4();
        allProducts.put(product.getProductType(), product);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(allProducts.values());
    }

    @Override
    public Map<String, Product> getAllProductsIndexedByProductType() {
        return Map.copyOf(allProducts);
    }

}
