package com.mthree.academy.co458.vrishti_va.flooring_mastery.view;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MainView {

    private UserIO userIO;

    public MainView(UserIO userIO) {
        this.userIO = userIO;
    }

    /* ----- Menu ----- */

    public int displayAndGetMenuSelection() {
        userIO.print(
            "\n<< Flooring Program >>" +
            "\n1. Display Orders" +
            "\n2. Add an Order" +
            "\n3. Edit an Order" +
            "\n4. Remove an Order" +
            "\n5. Export Active Orders" +
            "\n6. Quit"
        );
        return userIO.readInt("Enter Selection (#)", 1, 6);
    }

    public void displayQuittingProgram() {
        userIO.print("\nQuitting Program...");
    }

    public void displayOperationCancelledMessage() {
        userIO.print("\nOperation Cancelled.");
    }

    /**
     * Prompt the user to press enter to proceed
     */
    public void askToProceed() {
        userIO.readLine("Press enter to proceed...");
    }

    public boolean askYesNoQuestion(String prompt) {

        //Desired input
        String userInput;

        do {
            //Take decision input
            //Only want to consider first character, and avoid error due to no input.
            userInput = userIO.readString(prompt + " (Y/N)").trim();
            userInput = userInput.substring(0, Math.min(1, userInput.length())).toUpperCase();

            //Evaluate decision
            switch (userInput) {
                case "Y": return true;
                case "N": return false;
                default: userIO.print("Invalid input. Please indicate Y/N.");
            }

        } while (true);
    }

    /* ----- Warning Messages ----- */

    public void displayWarning(String message) {
        userIO.print("Warning: " + message);
    }

    public void displayUnknownMenuOptionWarning() { displayWarning("Unknown Menu Option."); }

    /* ----- Option Headers ----- */

    private void displayHeader(String header) {
        userIO.print("\n<< " + header + " >>");
    }

    public void displayDisplayOrdersHeader() { displayHeader("Display Orders"); }
    public void displayAddOrderHeader() { displayHeader("Add Order"); }
    public void displayEditOrderHeader() { displayHeader("Edit Order"); }
    public void displayRemoveOrderHeader() { displayHeader("Remove Order"); }
    public void displayExportActiveOrdersHeader() { displayHeader("Export Active Orders"); }

    /* ----- Operation Complete Messages ----- */

    public void displayAddOrderCompletedMessage(int orderNumber) {
        userIO.print("\nOrder has been added, with Order Number " + orderNumber + ".");
    }

    /* ----- Display Items ----- */

    public void displayOrder(Order order, boolean includeOrderNumber) {

        //Only display order number if provided
        if (includeOrderNumber) {
            userIO.print("- Order Number: " + order.getOrderNumber());
        }

        //Display remaining order details
        userIO.print(
            "\n- Customer Name: " + order.getCustomerName() +
            "\n- State: " + order.getState() +
            "\n- Tax Rate: " + order.getTaxRate() +
            "\n- Product Type: " + order.getProductType() +
            "\n- Area: " + order.getArea() +
            "\n- Cost Per Square Foot: " + order.getCostPerSquareFoot() +
            "\n- Labor Cost Per Square Foot: " + order.getLaborCostPerSquareFoot() +
            "\n- Material Cost: " + order.getMaterialCost() +
            "\n- Labor Cost: " + order.getLaborCost() +
            "\n- Tax: " + order.getTax() +
            "\n- Total: " + order.getTotal()
        );
    }

    public void displayOrdersForDate(LocalDate date, List<Order> orders) {

        if (orders.isEmpty())
            userIO.print("There are no orders to display for " + date.format(UserIO.DATE_FORMAT) + ".");

        else {
            userIO.print("Displaying orders for " + date.format(UserIO.DATE_FORMAT) + ":");
            userIO.print("");

            //Display each order one by one
            for (Order order : orders) {
                displayOrder(order, true);
            }

            userIO.print("");
        }
    }

    /**
     * Display a list of products.
     * @param products A list or another iterable collection of products.
     */
    public void displayProducts(Collection<Product> products) {

        //Display header
        String productsHeader = String.format("\n%-25s %25s %25s", "Product Type", "Cost Per Sq Ft", "Labor Cost Per Sq Ft");
        userIO.print(productsHeader);

        //Display product types
        String productLine;
        for (Product product : products) {
            productLine = String.format(
                "%-25s %25s %25s",
                product.getProductType(), product.getCostPerSquareFoot(), product.getLaborCostPerSquareFoot()
            );
            userIO.print(productLine);
        }
    }

    /* ----- Form Style Inputs ----- */

    public LocalDate askForOrderDate() {
        return userIO.readDate("Enter Order Date (" + UserIO.DATE_FORMAT_PATTERN + ")");
    }

    /**
     * Get new order details from user.
     * This method will ensure that the inputs provided are valid.
     * @param productTypes The available products, indexed by product type.
     * @param taxStates The available taxes, indexed by state.
     * @return If completed, the method returns an Order object containing the
     *         order date, customer name, state, product type, and area.
     *         Otherwise, null.
     *
     *
     * @implNote
     * Future consideration: Add an escape for these if user gets lazy and wishes to abandon operation.
     */
    public Order getNewOrder(Map<String, Product> productTypes, Map<String, Tax> taxStates) {

        //Ensure we don't trap the user if there are no products/taxes.
        if (productTypes.isEmpty()) {
            userIO.print("Unfortunately, you cannot add an order at the moment as there are no products available.");
            return null;
        }
        if (taxStates.isEmpty()) {
            userIO.print("Unfortunately, you cannot add an order at the moment as we aren't selling to any states.");
            return null;
        }

        //Desired inputs
        LocalDate orderDate;
        String customerName;
        String state;
        String productType;
        BigDecimal area;

        Tax tax;
        Product product;

        //Obtain a valid date between today and the future.
        orderDate = userIO.readDate("Enter Order Date (" + UserIO.DATE_FORMAT_PATTERN + ")", LocalDate.now(), null);

        //Obtain customer name
        do {
            customerName = userIO.readString("Enter Customer Name");
            customerName = validateCustomerNameInput(customerName);
        } while (customerName == null); //Maybe null is not the best invalid indicator, given that it can be optional.

        //Obtain a valid state
        do {
            state = userIO.readString("Enter State");
            tax = validateStateInput(state, taxStates);
        } while (tax == null);

        //Obtain a valid product type
        do {
            displayProducts(productTypes.values());
            productType = userIO.readString("Enter Product Type");
            product = validateProductTypeInput(productType, productTypes);
        } while (product == null);

        //Obtain a valid area
        do {
            area = userIO.readBigDecimal("Enter Area (Sq Ft)");
            area = validateAreaInput(area);
        } while (area == null);

        //Create new Order DTO
        Order newOrder = new Order();
        newOrder.setOrderDate(orderDate);
        newOrder.setCustomerName(customerName);
        newOrder.setState(tax.getStateName());
        newOrder.setTaxRate(tax.getTaxRate());
        newOrder.setProductType(product.getProductType());
        newOrder.setCostPerSquareFoot(product.getCostPerSquareFoot());
        newOrder.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        newOrder.setArea(area);

        //Return new order details
        return newOrder;
    }

    /**
     * Ask the user to confirm if they would like to add a specific order.
     * @param order The order to preview and confirm.
     * @return True for yes, False for no.
     */
    public boolean confirmAddOrder(Order order) {
        displayOrder(order, false);
        return askYesNoQuestion("Are you sure you wish to add this order?");
    }

    /* ----- User Input Validation ----- */

    /* Note: Why are these here and not in the service layer?
       Validation is often performed in both the front-end and back-end.
       The purpose of front-end validation is for user experience and understanding.
       The purpose of back-end validation is for data accuracy, security, and consistency.
       Backend validation is especially important when the user interface is something the user can manipulate
       e.g. user can inspect the page, disable front-end validation, and allow invalid data through.
       But in a console application, the "front-end" is not as manipulative.
       This allows the validation placed here to be sufficient to make the inputs docile.
     */

    /**
     * Validate the customer name, using the following validation rules:
     * <ul>
     *      <li> May not be blank </li>
     *      <li> Limited to characters [a-z][0-9] as well as periods and comma characters.
     *           e.g. "Acme, Inc." is a valid name. </li>
     * </ul>
     * @param customerName The customer name to validate.
     * @return The valid customer name, null if invalid.
     * @implNote Refresher reading on regex expressions <a href="https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html"></a>
     */
    private String validateCustomerNameInput(String customerName) {

        //Sanitize to prevent large spaces in names, by replacing all white spaces with single spaces.
        customerName = customerName.trim().replaceAll("\\s+", " ");

        if (customerName.isBlank()) {
            userIO.print("The customer name should not be blank. Please try again.");

        } else if (!customerName.matches("^[a-zA-Z0-9., ]+$")) {
            //If any character found in the string from not within the brackets, must be invalid.
            userIO.print("The customer name should only contain alphanumerics, periods, and/or commas. Please try again.");

        } else return customerName;

        return null;
    }

    /**
     * Validate that the state is selected from the available tax states.
     * @param state The state to validate.
     * @param taxStates The available tax states.
     * @return The corresponding Tax object for the tax state, null if invalid.
     */
    private Tax validateStateInput(String state, Map<String, Tax> taxStates) {

        //Get tax
        Tax tax = taxStates.get(state.trim().toLowerCase());

        //Validate tax
        if (tax != null) {
            return tax;
        } else {
            userIO.print("Invalid input. Please enter a supported state.");
            return null;
        }
    }

    /**
     * Validate that the product type is selected from the available product types.
     * @param productType The product type to evaluate.
     * @param products The available product types.
     * @return The corresponding Product object for the product type, null if invalid
     */
    private Product validateProductTypeInput(String productType, Map<String, Product> products) {

        //Get product
        Product product = products.get(productType.trim().toLowerCase());

        //Validate product
        if (product != null) {
            return product;
        } else {
            userIO.print("Invalid input. Please enter a product type from the list.");
            return null;
        }
    }

    /**
     * Validate that the area is a positive decimal that is at least 100 sq ft.
     * @param area The input area.
     * @return The valid area, otherwise null.
     */
    private BigDecimal validateAreaInput(BigDecimal area) {

        if (area.compareTo(BigDecimal.valueOf(100)) >= 0) {
            return area;
        } else {
            userIO.print("The minimum order size is 100 sq ft. Please try again.");
            return null;
        }
    }
}
