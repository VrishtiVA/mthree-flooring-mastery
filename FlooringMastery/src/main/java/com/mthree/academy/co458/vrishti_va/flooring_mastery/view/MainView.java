package com.mthree.academy.co458.vrishti_va.flooring_mastery.view;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.EditIntensity;
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
        return userIO.readInt("\nEnter Selection (#)", 1, 6);
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
        userIO.readLine("Press enter to proceed ...");
    }

    /**
     * Prompt the user to answer a Yes or No question, and indicate either Yes or No.
     * The method looks for indication using the first significant character in the input.
     * @param prompt The prompt to display.
     * @return True if indicated Yes, otherwise No.
     */
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

    private void displayWarning(String message) {
        userIO.print("\nWarning: " + message);
    }

    public void displayUnknownMenuOptionWarning() { displayWarning("\nUnknown Menu Option."); }

    public void displayEditOrderLostWarning() {displayWarning("The order to edit no longer exists.");}
    public void displayRemoveOrderLostWarning() {displayWarning("The order to remove no longer exists.");}
    public void displayAddOrderFailedWarning() {displayWarning("Unable to save new order.");}
    public void displayEditOrderFailedWarning() {displayWarning("Unable to save order changes.");}
    public void displayRemoveOrderFailedWarning() {displayWarning("Unable to save order removal.");}
    public void displayExportActiveOrdersFailedWarning() {displayWarning("Unable to export active orders.");}

    public void displayNoOrdersOnDateMessage(LocalDate date) {
        userIO.print("\nThere are no orders for " + date.format(UserIO.DATE_FORMAT) + ".");
    }

    public void displayNoSuchOrderMessage() {
        userIO.print("\nThere is no such order.");
    }

    public void displayNoEditsMadeMessage() {
        userIO.print("\nThere were no edits made.");
    }

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

    private void displaySuccess(String message) {userIO.print("\nSuccess: " + message);}

    public void displayAddOrderCompletedMessage(int orderNumber) {displaySuccess("Order has been added, with Order Number " + orderNumber + ".");}
    public void displayEditOrderCompletedMessage() {displaySuccess("Order has been modified.");}
    public void displayRemoveOrderCompletedMessage() {displaySuccess("Order has been removed.");}
    public void displayExportActiveOrdersCompleted() {displaySuccess("Active orders have been exported.");}

    /* ----- Display Items ----- */

    /**
     * Display the contents of a single order
     * @param order The order to display
     * @param includeOrderNumber True if the display should include the order number, otherwise false.
     */
    public void displayOrder(Order order, boolean includeOrderNumber) {

        //Only display order number if provided
        if (includeOrderNumber) {
            userIO.print("- Order Number: " + order.getOrderNumber());
        }

        //Display remaining order details
        userIO.print((includeOrderNumber ? "  " : "- ") +
            "Customer Name: " + order.getCustomerName() +
            "\n  Order Date: " + order.getOrderDate().format(UserIO.DATE_FORMAT) +
            "\n  State: " + order.getState() +
            "\n  Tax Rate: " + order.getTaxRate() +
            "\n  Area: " + order.getArea() +
            "\n  Product Type: " + order.getProductType() +
            "\n  Cost Per Square Foot: " + order.getCostPerSquareFoot() +
            "\n  Labor Cost Per Square Foot: " + order.getLaborCostPerSquareFoot() +
            "\n  Material Cost: " + order.getMaterialCost() +
            "\n  Labor Cost: " + order.getLaborCost() +
            "\n  Tax: " + order.getTax() +
            "\n  Total: " + order.getTotal()
        );
    }

    /**
     * Display multiple orders for the same date.
     * @param date The date to display
     * @param orders The orders to display
     */
    public void displayOrdersForDate(LocalDate date, List<Order> orders) {

        if (orders.isEmpty()) {
            displayNoOrdersOnDateMessage(date);

        } else {
            userIO.print("\nDisplaying orders for " + date.format(UserIO.DATE_FORMAT) + ":");

            //Display each order one by one
            for (Order order : orders) {
                userIO.print("");
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

    /**
     * Ask the user to enter an order date
     * @return The entered order date
     */
    public LocalDate askForOrderDate() {
        return userIO.readDate("\nEnter Order Date (" + UserIO.DATE_FORMAT_PATTERN + ")");
    }

    /**
     * Ask the user to enter an order number
     * @return The entered order date
     */
    public int askForOrderNumber() {
        return userIO.readInt("\nEnter Order Number");
    }

    /**
     * Get new order details from user.
     * This method will ensure that the inputs provided are valid.
     * @param productTypes The currently available products, indexed by product type.
     * @param taxStates The currently available taxes, indexed by state.
     * @return If completed, the method returns a populated Order object (without an order number) containing the
     *         order date, customer name, state, tax rate, product type, cost and labor cost per sq ft, and area.
     *         Otherwise, null.
     *
     * @implNote
     * Future consideration: Add an escape for these if user becomes lazy and wishes to abandon operation.
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
        orderDate = userIO.readDate("\nEnter Order Date (" + UserIO.DATE_FORMAT_PATTERN + ")", LocalDate.now(), null);

        //Obtain customer name
        do {
            customerName = userIO.readString("\nEnter Customer Name");
            customerName = validateCustomerNameInput(customerName);
        } while (customerName == null);

        //Obtain a valid state
        do {
            state = userIO.readString("\nEnter State Abbreviation");
            tax = validateStateInput(state, taxStates);
        } while (tax == null);

        //Obtain a valid product type
        do {
            displayProducts(productTypes.values());
            productType = userIO.readString("\nEnter Product Type");
            product = validateProductTypeInput(productType, productTypes);
        } while (product == null);

        //Obtain a valid area
        do {
            area = userIO.readBigDecimal("\nEnter Area (Sq Ft)", false);
            area = validateAreaInput(area);
        } while (area == null);

        //Create new Order DTO
        Order newOrder = new Order();
        newOrder.setOrderDate(orderDate);
        newOrder.setCustomerName(customerName);
        newOrder.setState(tax.getStateAbbreviation());
        newOrder.setTaxRate(tax.getTaxRate());
        newOrder.setProductType(product.getProductType());
        newOrder.setCostPerSquareFoot(product.getCostPerSquareFoot());
        newOrder.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        newOrder.setArea(area);

        //Return new order details
        return newOrder;
    }

    /**
     * Get the edited order details.
     * This method will collect updates and mutate the order object passed in,
     * therefore, it is highly recommended to pass in a copy of the object rather than the mutable object itself.
     * The method will indicate if recalculations are required, which is when updates are made to the product type, state, area.
     * @param productTypes The currently available products, indexed by product type.
     * @param taxStates The currently available taxes, indexed by state.
     * @return {@code EditIntensity.RECALCULATIONS_REQUIRED} if recalculations are required,
     *         {@code EditIntensity.EDITS_MADE} if edits were made but recalculations aren't required,
     *         {@code EditIntensity.NO_EDITS} if no edits were made.
     */
    public EditIntensity getOrderEdits(Order orderCopy, Map<String, Product> productTypes, Map<String, Tax> taxStates) {

        //Display edit order instructions
        userIO.print(
            "\nYou are now editing an order." +
            "\nFor each editable field, you will be prompted and shown the current value of the field." +
            "\nShould you wish to edit the field, input the new data." +
            "\nIf you do not wish to edit the field, simply press enter to continue." +
            "\nDon't worry if something goes wrong, you will be asked to confirm the edited order at the end."
        );
        askToProceed();

        //Desired inputs
        String customerName;
        String state;
        String productType;
        BigDecimal area;

        Tax tax;
        Product product;

        //Flags to indicate edits or if recalculations are needed.
        boolean editsMade = false;
        boolean recalculationsRequired = false;

        //Allow user to optionally edit customer name
        do {
            customerName = userIO.readString("\nEnter Customer Name (" + orderCopy.getCustomerName() + ")");

            //Skip if no input, otherwise validate
            if (customerName.isBlank()) break;
            customerName = validateCustomerNameInput(customerName);

            //If valid, hold valid field in order copy
            if (customerName != null) {
                orderCopy.setCustomerName(customerName);
                editsMade = true;
            }

        } while (customerName == null);

        if (!taxStates.isEmpty()) {
            //Allow user to optionally edit state
            do {
                state = userIO.readString("\nEnter State Abbreviation (" + orderCopy.getState() + ")");

                //Skip if no input, otherwise validate
                if (state.isBlank()) break;
                tax = validateStateInput(state, taxStates);

                //If valid, hold tax updates and indicate recalculation required.
                if (tax != null) {
                    orderCopy.setState(tax.getStateAbbreviation());
                    orderCopy.setTaxRate(tax.getTaxRate());
                    editsMade = true;
                    recalculationsRequired = true;
                }

            } while (tax == null);
        }

        if (!productTypes.isEmpty()) {
            //Allow user to optionally edit product type
            do {
                displayProducts(productTypes.values());
                productType = userIO.readString("\nEnter Product Type (" + orderCopy.getProductType() + ")");

                //Skip if no input, otherwise validate
                if (productType.isBlank()) break;
                product = validateProductTypeInput(productType, productTypes);

                //If valid, hold product updates and indicate recalculation required.
                if (product != null) {
                    orderCopy.setProductType(product.getProductType());
                    orderCopy.setCostPerSquareFoot(product.getCostPerSquareFoot());
                    orderCopy.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
                    editsMade = true;
                    recalculationsRequired = true;
                }

            } while (product == null);
        }

        //Allow user to optionally edit area
        do {
            area = userIO.readBigDecimal("\nEnter Area (" + orderCopy.getArea() + " Sq Ft)", true);

            //Skip if no input, otherwise validate
            if (area == null) break;
            area = validateAreaInput(area);

            //If valid, hold valid field in order copy
            if (area != null) {
                orderCopy.setArea(area);
                editsMade = true;
                recalculationsRequired = true;
            }

        } while (area == null);

        //Return intensity of edits made.
        return recalculationsRequired ? EditIntensity.RECALCULATIONS_REQUIRED :
                editsMade ? EditIntensity.EDITS_MADE :
                EditIntensity.NO_EDITS_MADE;
    }

    /* ----- Confirmations ----- */

    /**
     * Ask the user to confirm if they would like to add a specific order.
     * @param order The order to preview and confirm.
     * @return True for yes, False for no.
     */
    public boolean confirmAddOrder(Order order) {

        userIO.print("\nOrder Preview:");
        displayOrder(order, false);
        return askYesNoQuestion("\nAre you sure you wish to add this order?");
    }

    /**
     * Ask the user to confirm if they would like to update a specific order.
     * @param order The order to preview and confirm.
     * @return True for yes, False for no.
     */
    public boolean confirmEditOrder(Order order) {

        userIO.print("\nEdited Order Preview:");
        displayOrder(order, true);
        return askYesNoQuestion("\nAre you sure you wish to update this order?");
    }

    /**
     * Ask the user to confirm if they would like to remove a specific order.
     * @param order The order to preview and confirm.
     * @return True for yes, False for no.
     */
    public boolean confirmRemoveOrder(Order order) {

        userIO.print("\nOrder Preview:");
        displayOrder(order, true);
        return askYesNoQuestion("\nAre you sure you wish to remove this order?");
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
     * @param state The state abbreviation to validate.
     * @param taxStates The available tax states.
     * @return The corresponding Tax object for the tax state, null if invalid.
     */
    private Tax validateStateInput(String state, Map<String, Tax> taxStates) {

        //Get tax
        Tax tax = taxStates.get(state.trim().toUpperCase());

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
        Product product = products.get(productType.trim().toUpperCase());

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
