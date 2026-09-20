package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoFileImpl implements OrderDao {

    private static final String DELIMITER = "::";
    private final String ORDERS_DIRECTORY;
    private final String ORDERS_FILENAME_BASE;

    //Using a nested map for indexed behavior with dates and faster lookups.
    private Map<LocalDate, Map<Integer, Order>> allOrders;

    private int lastOrderNumber;

    public OrderDaoFileImpl(String ordersDirectory, String ordersFileNameBase) {

        this.allOrders = new HashMap<>();
        this.ORDERS_DIRECTORY = ordersDirectory;
        this.ORDERS_FILENAME_BASE = ordersFileNameBase;

        //Read file initially
        //...

        //Remember to set up last order number after file read. !
        this.lastOrderNumber = 0;
    }

    @Override
    public int getNextOrderNumber() {
        return ++lastOrderNumber;
    }

    @Override
    public Order addOrder(LocalDate orderDate, Order order) {

        //Get or create in-memory map for orders on date
        Map<Integer, Order> ordersOnDateMap = allOrders.computeIfAbsent(orderDate, key -> new HashMap<>());

        //Add order
        Order previousOrder = ordersOnDateMap.put(order.getOrderNumber(), order);

        //Persist order
        writeOrdersToFileByDate(orderDate);

        return previousOrder;
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNumber, Order editedOrder) throws NoSuchOrderException {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = allOrders.get(orderDate);
        Order originalOrder;

        try {
            //Try to replace order - replace would return null rather than replace a non-existing order.
            originalOrder = ordersOnDateMap.replace(orderNumber, editedOrder);
            if (originalOrder == null)
                throw new NoSuchOrderException("There is no such order to edit.");

            //Return the original order if reached here.
            return originalOrder;

        } catch (NullPointerException e) {
            //If there were no orders on this date, the order to edit doesn't exist.
            throw new NoSuchOrderException("There is no such order to edit.");
        }
    }


    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = allOrders.get(orderDate);
        Order removedOrder;

        try {
            //Try to remove order - remove would return null if order doesn't exist.
            removedOrder = ordersOnDateMap.remove(orderNumber);
            if (removedOrder == null)
                throw new NoSuchOrderException("There is no such order to remove.");

            //Return the removed order
            return removedOrder;

        } catch (NullPointerException e) {
            throw new NoSuchOrderException("There is no such order to remove.");
        }
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = allOrders.get(orderDate);
        Order retrievedOrder;

        try {
            //Try to get the order - would be null if it doesn't exist.
            retrievedOrder = ordersOnDateMap.get(orderNumber);
            if (retrievedOrder == null)
                throw new NoSuchOrderException("There is no such order to get.");

            //Return retrieved order
            return retrievedOrder;

        } catch (NullPointerException e) {
            throw new NoSuchOrderException("There is no such order to get.");
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = allOrders.get(date);

        //Return orders if found, otherwise empty list
        if (ordersOnDateMap != null)
            return new ArrayList<>(ordersOnDateMap.values());
        else
            return new ArrayList<>();
    }

    /**
     * Marshall an Order object into a String.
     * @param order The order to marshall.
     * @return The order contents in a string.
     */
    private String marshallOrder(Order order) {
        return order.getOrderNumber() + DELIMITER +
            order.getCustomerName() + DELIMITER +
            order.getState() + DELIMITER +
            order.getTaxRate() + DELIMITER +
            order.getProductType() + DELIMITER +
            order.getArea() + DELIMITER +
            order.getCostPerSquareFoot() + DELIMITER +
            order.getLaborCostPerSquareFoot() + DELIMITER +
            order.getMaterialCost() + DELIMITER +
            order.getLaborCost() + DELIMITER +
            order.getTax() + DELIMITER +
            order.getTotal();
    }

    /**
     * Unmarshall an Order string into an object.
     * This does not include the order date.
     * @param orderString The order string to unmarshall.
     * @return The corresponding order object.
     */
    private Order unmarshallOrder(String orderString) {

        //Split the order string
        String[] orderLine = orderString.split(DELIMITER);

        //Rebuild the order object.
        Order order = new Order(Integer.parseInt(orderLine[0]));
        order.setCustomerName(orderLine[1]);
        order.setState(orderLine[2]);
        order.setTaxRate(new BigDecimal(orderLine[3]));
        order.setProductType(orderLine[4]);
        order.setArea(new BigDecimal(orderLine[5]));
        order.setCostPerSquareFoot(new BigDecimal(orderLine[6]));
        order.setLaborCostPerSquareFoot(new BigDecimal(orderLine[7]));
        order.setMaterialCost(new BigDecimal(orderLine[8]));
        order.setLaborCost(new BigDecimal(orderLine[9]));
        order.setTax(new BigDecimal(orderLine[10]));
        order.setTotal(new BigDecimal(orderLine[11]));

        //Return rebuilt order object
        return order;
    }

    /**
     * Read in all files in the orders folder, and populate orders map.
     */
    private void loadAllOrdersFromFiles() {

    }

    /**
     * Method to rewrite the orders file for a certain date.
     * @param orderDate The order date
     * @throws PersistenceException If unable to open the file to write in.
     */
    private void writeOrdersToFileByDate(LocalDate orderDate) throws PersistenceException {

        //Open correct file in write mode
        PrintWriter printWriter;
        String fileName = ORDERS_DIRECTORY + "/" + ORDERS_FILENAME_BASE + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
        try {
            printWriter = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new PersistenceException("Unable to save orders.");
        }

        //Write header to file
        printWriter.println(
            "OrderNumber" + DELIMITER +
            "CustomerName" + DELIMITER +
            "State" + DELIMITER +
            "TaxRate" + DELIMITER +
            "ProductType" + DELIMITER +
            "Area" + DELIMITER +
            "CostPerSquareFoot" + DELIMITER +
            "LaborCostPerSquareFoot" + DELIMITER +
            "MaterialCost" + DELIMITER +
            "LaborCost" + DELIMITER +
            "Tax" + DELIMITER +
            "Total"
        );

        //Write each order on date to file
        String orderString;
        for (Order order : getOrdersByDate(orderDate)) {

            //Marshall order
            orderString = marshallOrder(order);

            //Write order immediately
            printWriter.println(orderString);
            printWriter.flush();
        }

        //Clean up
        printWriter.close();
    }
}
