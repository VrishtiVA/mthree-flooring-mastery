package com.mthree.academy.co458.vrishti_va.flooring_mastery.dao;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class OrderDaoFileImpl implements OrderDao {

    private static final String DELIMITER = "::";
    private final String ORDERS_DIRECTORY;
    private final String ORDERS_FILE_NAME_BASE;
    private final String ORDERS_FILE_EXTENSION;

    //Using a nested map for indexed behavior with dates and faster lookups.
    private Map<LocalDate, Map<Integer, Order>> allOrders;

    private int lastOrderNumber;

    public OrderDaoFileImpl(String ordersDirectory, String ordersFileNameBase, String ordersFileExtension) {

        this.allOrders = new HashMap<>();
        this.ORDERS_DIRECTORY = ordersDirectory;
        this.ORDERS_FILE_NAME_BASE = ordersFileNameBase;
        this.ORDERS_FILE_EXTENSION = ordersFileExtension;

        //Read file initially and set up last order number.
        loadAllOrdersFromFiles();
        this.lastOrderNumber = findLastOrderNumber();
    }

    /**
     * Get the next order number that can be used for an order.
     * @return A new order number
     * @implNote The implementation does not recycle order numbers.
     */
    @Override
    public int getNextOrderNumber() {
        return ++lastOrderNumber;
    }

    @Override
    public Order addOrder(LocalDate orderDate, Order order) throws PersistenceException {

        //Get or create in-memory map for orders on date
        Map<Integer, Order> ordersOnDateMap = allOrders.computeIfAbsent(orderDate, key -> new HashMap<>());

        //Add order
        Order previousOrder = ordersOnDateMap.put(order.getOrderNumber(), order);

        //Persist order
        writeOrdersToFileByDate(orderDate);

        return previousOrder;
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNumber, Order editedOrder) throws NoSuchOrderException, PersistenceException {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = allOrders.get(orderDate);
        Order originalOrder;

        try {
            //Try to replace order - replace would return null rather than replace a non-existing order.
            originalOrder = ordersOnDateMap.replace(orderNumber, editedOrder);
            if (originalOrder == null)
                throw new NoSuchOrderException("There is no such order to edit.");

            //Persist edits
            writeOrdersToFileByDate(orderDate);

            //Return the original order if reached here.
            return originalOrder;

        } catch (NullPointerException e) {
            //If there were no orders on this date, the order to edit doesn't exist.
            throw new NoSuchOrderException("There is no such order to edit.");
        }
    }


    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws NoSuchOrderException, PersistenceException {

        //Find orders on date
        Map<Integer, Order> ordersOnDateMap = allOrders.get(orderDate);
        Order removedOrder;

        try {
            //Try to remove order - remove would return null if order doesn't exist.
            removedOrder = ordersOnDateMap.remove(orderNumber);
            if (removedOrder == null)
                throw new NoSuchOrderException("There is no such order to remove.");

            //Persist removal
            writeOrdersToFileByDate(orderDate);

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
     * Get all active orders (orders with an order date of today or in the future).
     * @return A list of all active orders.
     * @implNote Using flat map to flatten {@code Collection<Collection<Order>>} into {@code Collection<Order>}.
     *           <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#flatMap-java.util.function.Function-">Refreshing documentation</a>
     */
    @Override
    public List<Order> getAllActiveOrders() {
        return allOrders.keySet().stream()
                .filter(orderDate -> !orderDate.isBefore(LocalDate.now()))
                .flatMap(orderDate -> allOrders.get(orderDate).values().stream())
                .collect(Collectors.toList());

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
    private Order unmarshallOrder(String orderString, LocalDate orderDate) {

        //Split the order string
        String[] orderLine = orderString.split(DELIMITER);

        //Rebuild the order object.
        Order order = new Order(Integer.parseInt(orderLine[0]));
        order.setOrderDate(orderDate);
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
     * @implNote <a href="https://docs.oracle.com/javase/8/docs/api/java/io/File.html">Refreshing documentation</a>
     */
    private void loadAllOrdersFromFiles() throws PersistenceException {

        //Find orders directory and gather all order files.
        File ordersDirectory = new File(ORDERS_DIRECTORY);
        File[] orderFiles;
        try {
            //Ensure directory exists
            if (!ordersDirectory.exists() || !ordersDirectory.isDirectory())
                throw new FileNotFoundException("Unable to find Orders.");

            //Get list of order files
            orderFiles = ordersDirectory.listFiles();
            if (orderFiles == null)
                throw new FileNotFoundException("Unable to find Orders");

        } catch (SecurityException | FileNotFoundException e) {
            //List files can throw a security exception if it can't access.
            throw new PersistenceException("Unable to access Orders");
        }

        //Read and load orders from each file
        for (File file : orderFiles) {
            loadOrdersFromFile(file.getName());
        }

    }

    private void loadOrdersFromFile(String filename) throws PersistenceException {

        //Work out date to populate for
        LocalDate orderDate;
        try {
            orderDate = LocalDate.parse(filename.substring(7, 15), DateTimeFormatter.ofPattern("MMddyyyy"));
        } catch (DateTimeParseException e) {
            throw new PersistenceException("Unable to load Orders.");
        }

        //Try open file in read mode.
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new BufferedReader(new FileReader(ORDERS_DIRECTORY + "/" + filename)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Unable to load Orders.");
        }

        //Skip header row
        fileScanner.nextLine();

        //Read each order and populate map
        String orderString;
        Order order;
        while (fileScanner.hasNextLine()) {

            //Unmarshall order
            orderString = fileScanner.nextLine();
            order = unmarshallOrder(orderString, orderDate);

            //Populate map
            allOrders.computeIfAbsent(orderDate, key -> new HashMap<>()).put(order.getOrderNumber(), order);
        }
    }

    /**
     * Method to rewrite the orders file for a certain date.
     * @param orderDate The order date
     * @throws PersistenceException If unable to open the file to write in.
     */
    private void writeOrdersToFileByDate(LocalDate orderDate) throws PersistenceException {

        //Open correct file in write mode
        PrintWriter printWriter;
        String fileName = ORDERS_DIRECTORY + "/" + ORDERS_FILE_NAME_BASE + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ORDERS_FILE_EXTENSION;
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

    private int findLastOrderNumber() {

        //If no orders, shortcut highest as 0.
        if (allOrders.isEmpty()) return 0;

        OptionalInt highestOrderNumber = allOrders.values().stream()
                .flatMap(ordersOnDate -> ordersOnDate.values().stream()) //Flatten into collection of orders
                .mapToInt(Order::getOrderNumber) //Simplify into collection of order numbers
                .max(); //Find maximum order number

        return highestOrderNumber.isPresent() ? highestOrderNumber.getAsInt() : 0;
    }
}
