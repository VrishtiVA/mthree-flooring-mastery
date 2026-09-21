package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleProduct;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleTax;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoFileImplTest {

    private static final String TEST_PRODUCT_FILE = "TestData/Products.txt";
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        this.productDao = new ProductDaoFileImpl(TEST_PRODUCT_FILE);
    }

    /**
     * Test that all expected products are returned in a list.
     */
    @Test
    void testGetAllProducts() {

        //Act
        List<Product> allProducts = productDao.getAllProducts();

        //Assert
        assertNotNull(allProducts, "The returned products list should not be null");
        assertEquals(4, allProducts.size(), "The returned products list should contain 4 products");
        assertTrue(allProducts.containsAll(List.of(
                SampleProduct.getSampleProduct1(),
                SampleProduct.getSampleProduct2(),
                SampleProduct.getSampleProduct3(),
                SampleProduct.getSampleProduct4()
        )));
    }

    /**
     * Test that all expected products are returned in a map
     */
    @Test
    void testGetAllProductsIndexedByProductType() {

        //Act
        Map<String, Product> allProducts = productDao.getAllProductsIndexedByProductType();

        //Assert
        assertNotNull(allProducts, "The returned products map should not be null.");
        assertEquals(4, allProducts.size(), "The returned products map should contain 4 products");
        assertEquals(SampleProduct.getSampleProduct1(), allProducts.get(SampleProduct.getSampleProduct1().getProductType().toUpperCase()));
        assertEquals(SampleProduct.getSampleProduct2(), allProducts.get(SampleProduct.getSampleProduct2().getProductType().toUpperCase()));
        assertEquals(SampleProduct.getSampleProduct3(), allProducts.get(SampleProduct.getSampleProduct3().getProductType().toUpperCase()));
        assertEquals(SampleProduct.getSampleProduct4(), allProducts.get(SampleProduct.getSampleProduct4().getProductType().toUpperCase()));
    }
}