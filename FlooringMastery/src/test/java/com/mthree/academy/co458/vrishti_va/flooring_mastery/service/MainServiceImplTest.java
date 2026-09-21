package com.mthree.academy.co458.vrishti_va.flooring_mastery.service;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.NoSuchOrderException;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleOrder;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleProduct;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleTax;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainServiceImplTest {

    private MainService mainService;

    public MainServiceImplTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.mainService = applicationContext.getBean("mainService", MainService.class); //Interface type
    }

    @Test
    void testCalculateOrderCosts() {

        //Arrange
        Order order = SampleOrder.getSampleOrder1();

        //Act
        Order orderCalculationsUpdated = mainService.calculateOrderCosts(order);

        //Assert - updated calculations match valid object
        assertEquals(order, orderCalculationsUpdated, "Calculation updates should match the valid object.");
    }

    @Test
    void testGetAllTaxes() {

        //Act
        List<Tax> allTaxes = mainService.getAllTaxes();

        //Assert
        assertNotNull(allTaxes, "The list of tax states should not be null");
        assertTrue(allTaxes.contains(SampleTax.getSampleTax1()));
        assertTrue(allTaxes.contains(SampleTax.getSampleTax2()));
        assertTrue(allTaxes.contains(SampleTax.getSampleTax3()));
        assertTrue(allTaxes.contains(SampleTax.getSampleTax4()));
    }

    @Test
    void testGetAllTaxesIndexedByState() {

        //Act
        Map<String, Tax> allTaxes = mainService.getAllTaxesIndexedByState();

        //Assert
        assertNotNull(allTaxes);
        assertTrue(allTaxes.containsValue(SampleTax.getSampleTax1()));
        assertTrue(allTaxes.containsValue(SampleTax.getSampleTax2()));
        assertTrue(allTaxes.containsValue(SampleTax.getSampleTax3()));
        assertTrue(allTaxes.containsValue(SampleTax.getSampleTax4()));
    }

    @Test
    void testGetAllProducts() {

        //Act
        List<Product> allProducts = mainService.getAllProducts();

        //Assert
        assertNotNull(allProducts);
        assertTrue(allProducts.contains(SampleProduct.getSampleProduct1()));
        assertTrue(allProducts.contains(SampleProduct.getSampleProduct2()));
        assertTrue(allProducts.contains(SampleProduct.getSampleProduct3()));
        assertTrue(allProducts.contains(SampleProduct.getSampleProduct4()));
    }

    @Test
    void testGetAllProductsIndexedByProductType() {

        //Act
        Map<String, Product> allProducts = mainService.getAllProductsIndexedByProductType();

        //Assert
        assertNotNull(allProducts);
        assertTrue(allProducts.containsValue(SampleProduct.getSampleProduct1()));
        assertTrue(allProducts.containsValue(SampleProduct.getSampleProduct2()));
        assertTrue(allProducts.containsValue(SampleProduct.getSampleProduct3()));
        assertTrue(allProducts.containsValue(SampleProduct.getSampleProduct4()));
    }

    @Test
    void testGetOrdersByDate() {

        //Arrange
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order1 = new Order(SampleOrder.getSampleOrder2(), 1);
        Order order2 = new Order(SampleOrder.getSampleOrder3(), 2);
        order1.setOrderDate(orderDate);
        order2.setOrderDate(orderDate);

        //Act
        List<Order> ordersOnDate = mainService.getOrdersByDate(orderDate);

        //Assert
        assertNotNull(ordersOnDate);
        assertEquals(2, ordersOnDate.size());
        assertTrue(ordersOnDate.contains(order1));
        assertTrue(ordersOnDate.contains(order2));
    }

    @Test
    void testGetOrderExisting() {

        //Arrange
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order1 = new Order(SampleOrder.getSampleOrder2(), 1);
        order1.setOrderDate(orderDate);

        //Act
        Order retrievedOrder = mainService.getOrder(orderDate, 1);

        //Assert
        assertEquals(order1, retrievedOrder);
    }

    @Test
    void getOrderNotExisting() {

        //Arrange
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order1 = new Order(SampleOrder.getSampleOrder2(), 1);
        order1.setOrderDate(orderDate);

        try {
            //Act
            mainService.getOrder(orderDate, 4);

            //Assert
            fail("Should not be able to get non-existent order");

        } catch (NoSuchOrderException e) { return;
        } catch (Exception e) {
            fail("Incorrect exception was thrown.");
        }
    }

    @Test
    void testAddOrderNew() {

        //Arrange
        Order order = SampleOrder.getSampleOrder1();

        //Act
        int orderNumber = mainService.addOrder(order);

        //Assert
        assertEquals(3, orderNumber, "The next assigned order number should be 3.");
    }

    @Test
    void testAddOrderAttemptOverride() {

        //Arrange
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order = mainService.getOrder(orderDate, 1);
        order.setCustomerName("Jane Doe");

        //Act
        int orderNumber = mainService.addOrder(order);

        //Assert
        assertEquals(3, orderNumber, "The next assigned order number should be 3, as the order is not overriden when adding.");
    }

    @Test
    void testEditOrderExisting() {

        //Arrange
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order order = mainService.getOrder(orderDate, 1);
        Order editedOrder = new Order(order, order.getOrderNumber());
        editedOrder.setCustomerName("Jane Doe");

        //Act
        try {
            mainService.editOrder(orderDate, editedOrder);

        } catch (Exception e) {
            //Assert
            fail("An exception shouldn't have been thrown", e);
        }
    }

    @Test
    void testEditOrderNonExisting() {

        //Arrange
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));
        Order editedOrder = new Order(SampleOrder.getSampleOrder1(), 3);

        //Act
        try {
            mainService.editOrder(orderDate, editedOrder);

            //Assert
            fail("Should not silently edit a non-existing order.");

        } catch (NoSuchOrderException e) { return;
        } catch (Exception e) {
            //Assert
            fail("Incorrect exception was thrown", e);
        }
    }

    @Test
    void testRemoveOrderExisting() {

        //Arrange
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));

        try {
            //Act
            mainService.removeOrder(orderDate, 1);

        } catch (Exception e) {
            //Assert
            fail("An exception shouldn't have been thrown", e);
        }
    }

    @Test
    void testRemoveOrderNonExisting() {

        //Arrange
        LocalDate orderDate = LocalDate.parse("01012028", DateTimeFormatter.ofPattern("MMddyyyy"));

        //Act
        try {
            mainService.removeOrder(orderDate, 10);

            //Assert
            fail("Should not silently remove a non-existing order.");

        } catch (NoSuchOrderException e) { return;
        } catch (Exception e) {
            //Assert
            fail("Incorrect exception was thrown", e);
        }
    }

    @Test
    void exportActiveOrders() {

        try {
            //Act
            mainService.exportActiveOrders();

        } catch (Exception e) {
            //Assert
            fail("An unexpected exception was thrown", e);
        }
    }
}