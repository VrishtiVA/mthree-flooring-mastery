package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dto.SampleOrder;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 /*
 * Test Plan:
 * testGetNextOrderNumber
 * - testGetNextOrderNumberInitial() -> 1
 * - testGetNextOrderNumberNotInitial() -> 2 after adding another order first.
 * testAddOrder
 * - testAddOrderInitial() -> Initial order added should previously be null, be retrievable after being added.
 * - testAddOrderOverride() -> The previous order should be returned, and the override should be retrievable.
 * - testAddOrderSameDate() -> Should be able to retrieve the correct order added, even when another order for that date also exists.
 * testEditOrder
 * - testEditOrderReplaces() -> The original order should be returned, and the edited order should be retrievable.
 * - testEditOrderNoSuchOrder() -> Throws No Such Order
 * testRemoveOrder
 * - testRemoveOrderRemoves() -> The removed order should be returned, and the removed order should not be retrievable.
 * - testRemoveOrderNoSuchOrder() -> Throws No Such Order
 * testGetOrder
 * - testGetOrder() -> When multiple orders exist, the retrieved order should match the order to get.
 * - testGetOrderNoSuchOrder() -> Throws No Such Order
 * testGetOrdersByDate()
 * - testGetOrdersByDate() -> When multiple orders exist (2 on date, 1 on another date), only a list containing the orders on the date should be returned.
 * - testGetOrdersByDateEmpty() -> An empty list that is not null
 * testGetAllActiveOrders
 * - testGetAllActiveOrders() -> When multiple orders exist (2 on active, 1 inactive), only a list containing the active orders should be returned.
 * - testGetAllActiveOrdersEmpty() -> An empty list that is not null
 */
class OrderDaoFileImplTest {

    private static final String TEST_ORDERS_DIRECTORY_NAME = "TestOrders";
    private static final String TEST_ORDERS_FILE_NAME_BASE = "Orders_";
    private static final String TEST_ORDERS_FILE_EXTENSION = ".txt";

    private static File TEST_ORDERS_DIRECTORY;
    private OrderDao orderDao;

    @BeforeAll
    static void beforeAll() {
        //Initialize a pointer to the directory.
        TEST_ORDERS_DIRECTORY = new File(TEST_ORDERS_DIRECTORY_NAME);
    }

    /**
     * A utility method to get the order file name, including the directory.
     * @return The order file name.
     */
    private String getOrderFullFileName(String filename) {
        return TEST_ORDERS_DIRECTORY_NAME + "/" + TEST_ORDERS_FILE_NAME_BASE + TEST_ORDERS_FILE_EXTENSION;
    }

    /**
     * A utility method to empty the test order's directory.
     */
    private void emptyTestOrdersDirectory() {
        File[] testOrderFiles = TEST_ORDERS_DIRECTORY.listFiles();
        if (testOrderFiles == null) return;

        for (File file : testOrderFiles) {
            file.delete();
        }
    }

    @BeforeEach
    void setUp() {
        emptyTestOrdersDirectory();
        this.orderDao = new OrderDaoFileImpl(TEST_ORDERS_DIRECTORY_NAME, TEST_ORDERS_FILE_NAME_BASE, TEST_ORDERS_FILE_EXTENSION);
    }

    @Test
    void testGetNextOrderNumberInitial() {

        //Act
        int orderNumber = orderDao.getNextOrderNumber();
        //Assert
        assertEquals(1, orderNumber, "The initial order number should be 1");
    }

    @Test
    void testGetNextOrderNumberNotInitial() {

        //Arrange
        Order order = SampleOrder.getSampleOrder1();
        order = new Order(order, orderDao.getNextOrderNumber());
        orderDao.addOrder(order.getOrderDate(), order);

        //Act
        int orderNumber = orderDao.getNextOrderNumber();

        //Assert
        assertEquals(2, orderNumber, "The order number after one order already exists, should be 2.");
    }

    @Test
    void testAddOrderInitial() {

        //Arrange
        Order newOrder = SampleOrder.getSampleOrder1();
        newOrder = new Order(newOrder, orderDao.getNextOrderNumber());

        //Act
        Order previousOrder = orderDao.addOrder(newOrder.getOrderDate(), newOrder);
        Order retrievedOrder = orderDao.getOrder(newOrder.getOrderDate(), newOrder.getOrderNumber());

        //Assert
        assertNull(previousOrder, "The previous order should be null as nothing should be previously there.");
        assertEquals(newOrder, retrievedOrder, "Should be able to retrieve the same new order.");
    }

    /**
     * Override add allowed by the DAO but not used by the system.
     */
    @Test
    void testAddOrderOverride() {

        //Arrange - Add initial order
        Order order = SampleOrder.getSampleOrder1();
        order = new Order(order, orderDao.getNextOrderNumber());
        orderDao.addOrder(order.getOrderDate(), order);

        //Arrange - an altered order with same order number
        Order newOrder = new Order(order, order.getOrderNumber());
        newOrder.setCustomerName("John Doe");

        //Act - override the order
        Order previousOrder = orderDao.addOrder(newOrder.getOrderDate(), newOrder);
        Order retrievedOrder = orderDao.getOrder(newOrder.getOrderDate(), newOrder.getOrderNumber());

        //Assert
        assertEquals(order, previousOrder, "The previous order should be the original order.");
        assertEquals(newOrder, retrievedOrder, "The retrieved order should match the override.");
    }

    /**
     * Override add allowed by the DAO but not used by the system.
     */
    @Test
    void testAddOrderSameDate() {

        //Arrange - Add initial order
        Order order = SampleOrder.getSampleOrder2();
        order = new Order(order, orderDao.getNextOrderNumber());
        orderDao.addOrder(order.getOrderDate(), order);

        //Arrange - Prepare another order with the same date
        Order order2 = SampleOrder.getSampleOrder3();
        order2 = new Order(order2, orderDao.getNextOrderNumber());

        //Act - Add new order
        Order previousOrder = orderDao.addOrder(order2.getOrderDate(), order2);
        Order retrievedOrder = orderDao.getOrder(order2.getOrderDate(), order2.getOrderNumber());

        //Assert
        assertNull(previousOrder, "The previous order should be null as nothing should be previously there.");
        assertEquals(order2, retrievedOrder, "Should be able to retrieve the same new order.");
    }

    @Test
    void testEditOrderReplaces() {

        //Arrange - Add initial order
        Order order = SampleOrder.getSampleOrder1();
        order = new Order(order, orderDao.getNextOrderNumber());
        orderDao.addOrder(order.getOrderDate(), order);

        //Arrange - An edited copy of the order
        Order editedOrder = new Order(SampleOrder.getSampleOrder2(), order.getOrderNumber());
        editedOrder.setOrderDate(order.getOrderDate());

        //Act - edit the order
        Order originalOrder = orderDao.editOrder(order.getOrderDate(), order.getOrderNumber(), editedOrder);
        Order retrievedOrder = orderDao.getOrder(order.getOrderDate(), order.getOrderNumber());

        //Assert
        assertEquals(order, originalOrder, "The previous order should be the original order.");
        assertEquals(editedOrder, retrievedOrder, "The retrieved order should match the edited order.");
    }

    @Test
    void testEditOrderNoSuchOrder() {

        //Arrange - An order
        Order order = SampleOrder.getSampleOrder1();
        order = new Order(order, orderDao.getNextOrderNumber());

        try {
            //Act - Edit the order
            orderDao.editOrder(order.getOrderDate(), order.getOrderNumber(), order);
            //Assert
            fail("Should not silently edit non-existing order");

        } catch (NoSuchOrderException e) { return;
        } catch (Exception e) {
            fail("Incorrect exception was thrown");
        }
    }

    @Test
    void testRemoveOrderRemoved() {

        //Arrange - Add initial order
        Order order = SampleOrder.getSampleOrder1();
        order = new Order(order, orderDao.getNextOrderNumber());
        orderDao.addOrder(order.getOrderDate(), order);

        //Act - Remove order
        Order removedOrder = orderDao.removeOrder(order.getOrderDate(), order.getOrderNumber());

        //Assert
        assertEquals(order, removedOrder, "The order removed should match the order that was there.");

        try {
            //Act - Try get removed order
            Order retrievedOrder = orderDao.getOrder(order.getOrderDate(), order.getOrderNumber());
            //Assert
            fail("Should not be able to get an order that was removed");

        } catch (NoSuchOrderException e) { return;
        } catch (Exception e) {
            fail("Incorrect exception was thrown.");
        }
    }

    @Test
    void testRemoveOrderNoSuchOrder() {

        //Arrange - Add initial order
        Order order = SampleOrder.getSampleOrder1();
        order = new Order(order, orderDao.getNextOrderNumber());

        try {
            //Act - Remove order
            orderDao.removeOrder(order.getOrderDate(), order.getOrderNumber());
            //Assert
            fail("Should not silently remove non-existing order");

        } catch (NoSuchOrderException e) { return;
        } catch (Exception e) {
            fail("Incorrect exception was thrown");
        }
    }

    @Test
    void testGetOrderExisting() {

        //Arrange - Add initial order
        Order order = SampleOrder.getSampleOrder2();
        order = new Order(order, orderDao.getNextOrderNumber());
        orderDao.addOrder(order.getOrderDate(), order);

        //Arrange - Add another order
        Order order2 = SampleOrder.getSampleOrder3();
        order2 = new Order(order2, orderDao.getNextOrderNumber());
        orderDao.addOrder(order2.getOrderDate(), order2);

        //Act
        Order retrievedOrder = orderDao.getOrder(order2.getOrderDate(), order2.getOrderNumber());

        //Assert
        assertEquals(order2, retrievedOrder, "Retrieved order should match order to get.");
    }

    @Test
    void testGetOrdersByDate() {

        //Arrange
        Order order1 = new Order(SampleOrder.getSampleOrder1(), orderDao.getNextOrderNumber());
        Order order2 = new Order(SampleOrder.getSampleOrder2(), orderDao.getNextOrderNumber());
        Order order3 = new Order(SampleOrder.getSampleOrder3(), orderDao.getNextOrderNumber());
        orderDao.addOrder(order1.getOrderDate(), order1);
        orderDao.addOrder(order2.getOrderDate(), order2);
        orderDao.addOrder(order3.getOrderDate(), order3);

        //Act
        List<Order> ordersOnDate = orderDao.getOrdersByDate(order2.getOrderDate());

        //Assert
        assertNotNull(ordersOnDate, "List of orders should not be null.");
        assertEquals(2, ordersOnDate.size(), "There should have been 2 orders in the returned list.");
        assertTrue(ordersOnDate.contains(order2), "Order 2 should have been in the list");
        assertTrue(ordersOnDate.contains(order3), "Order 3 should have been in the list" );
        assertFalse(ordersOnDate.contains(order1), "Order 1 should not have been in the list");
    }

    @Test
    void testGetOrdersByDateEmpty() {

        //Act
        List<Order> ordersOnDate = orderDao.getOrdersByDate(LocalDate.parse("01-01-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        //Assert
        assertNotNull(ordersOnDate, "List of orders should not be null.");
        assertEquals(0, ordersOnDate.size(), "List of orders should be empty.");
    }

    @Test
    void testGetAllActiveOrders() {

        //Arrange - Add 3 orders (2 active, 1 inactive)
        Order order1 = new Order(SampleOrder.getSampleOrder1(), orderDao.getNextOrderNumber());
        Order order2 = new Order(SampleOrder.getSampleOrder2(), orderDao.getNextOrderNumber());
        Order order3 = new Order(SampleOrder.getSampleOrder3(), orderDao.getNextOrderNumber());
        order2.setOrderDate(LocalDate.now().plusDays(5));
        order3.setOrderDate(LocalDate.now().plusDays(7));
        orderDao.addOrder(order1.getOrderDate(), order1);
        orderDao.addOrder(order2.getOrderDate(), order2);
        orderDao.addOrder(order3.getOrderDate(), order3);

        //Act
        List<Order> activeOrders = orderDao.getAllActiveOrders();

        //Assert
        assertNotNull(activeOrders, "List of active orders should not be null.");
        assertEquals(2, activeOrders.size(), "There should be 2 active orders found.");
        assertTrue(activeOrders.contains(order2), "Order 2 should have been in the list");
        assertTrue(activeOrders.contains(order3), "Order 3 should have been in the list" );
        assertFalse(activeOrders.contains(order1), "Order 1 should not have been in the list");
    }

    @Test
    void testGetAllActiveOrdersEmpty() {

        //Arrange - Add inactive orders
        Order order1 = new Order(SampleOrder.getSampleOrder1(), orderDao.getNextOrderNumber());
        Order order2 = new Order(SampleOrder.getSampleOrder2(), orderDao.getNextOrderNumber());
        orderDao.addOrder(order1.getOrderDate(), order1);
        orderDao.addOrder(order2.getOrderDate(), order2);

        //Act
        List<Order> activeOrders = orderDao.getAllActiveOrders();

        //Assert
        assertNotNull(activeOrders, "List of active orders should not be null.");
        assertEquals(0, activeOrders.size(), "List of active orders should be empty.");
    }

}