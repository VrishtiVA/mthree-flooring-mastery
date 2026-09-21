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

    @Test
    void testGetAllProducts() {

        //Act
        List<Product> allProducts = productDao.getAllProducts();

        //Assert
        assertNotNull(allProducts);
        assertEquals(4, allProducts.size());
        assertTrue(allProducts.containsAll(List.of(
                SampleProduct.getSampleProduct1(),
                SampleProduct.getSampleProduct2(),
                SampleProduct.getSampleProduct3(),
                SampleProduct.getSampleProduct4()
        )));
    }

    @Test
    void getAllProductsIndexedByProductType() {

        //Act
        Map<String, Product> allProducts = productDao.getAllProductsIndexedByProductType();

        //Assert
        assertNotNull(allProducts);
        assertEquals(4, allProducts.size());
        assertEquals(allProducts.get(SampleProduct.getSampleProduct1().getProductType().toUpperCase()), SampleProduct.getSampleProduct1());
        assertEquals(allProducts.get(SampleProduct.getSampleProduct2().getProductType().toUpperCase()), SampleProduct.getSampleProduct2());
        assertEquals(allProducts.get(SampleProduct.getSampleProduct3().getProductType().toUpperCase()), SampleProduct.getSampleProduct3());
        assertEquals(allProducts.get(SampleProduct.getSampleProduct4().getProductType().toUpperCase()), SampleProduct.getSampleProduct4());
    }
}