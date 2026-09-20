package com.mthree.academy.co458.vrishti_va.flooring_mastery.controller;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.NoSuchOrderException;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.PersistenceException;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.EditIntensity;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Product;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Tax;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.service.MainService;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.view.MainView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * This controller orchestrates the main/overall program.
 */
public class MainController {

    private MainView view;
    private MainService service;

    public MainController(MainView view, MainService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean keepGoing = true;
        do {
            switch (view.displayAndGetMenuSelection()) {
                case 1:
                    displayOrdersRoutine();
                    break;
                case 2:
                    addOrderRoutine();
                    break;
                case 3:
                    editOrderRoutine();
                    break;
                case 4:
                    removeOrderRoutine();
                    break;
                case 5:
                    exportActiveOrders();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    view.displayUnknownMenuOptionWarning();
            }
            view.askToProceed();

        } while (keepGoing);

        view.displayQuittingProgram();
    }

    private void displayOrdersRoutine() {
        view.displayDisplayOrdersHeader();

        //Ask for order date
        LocalDate orderDate = view.askForOrderDate();

        //Get appropriate orders
        List<Order> orders = service.getOrdersByDate(orderDate);

        //Display orders
        view.displayOrdersForDate(orderDate, orders);
    }

    private void addOrderRoutine() {
        view.displayAddOrderHeader();

        //Get updated product types and tax states
        Map<String, Product> productTypes = service.getAllProductsIndexedByProductType();
        Map<String, Tax> taxStates = service.getAllTaxesIndexedByState();

        //Ask for valid order inputs
        Order newOrder = view.getNewOrder(productTypes, taxStates);
        if (newOrder == null) return;

        //Calculate costs
        newOrder = service.calculateOrderCosts(newOrder);

        //Ask for confirmation to add order
        int orderNumber;
        if (view.confirmAddOrder(newOrder)) {
            orderNumber = service.addOrder(newOrder);
            view.displayAddOrderCompletedMessage(orderNumber);
        } else {
            view.displayOperationCancelledMessage();
        }
    }

    /**
     * @implNote To be adjusted to add EXIT option to abandon (indicated by design).
     */
    private void editOrderRoutine() {
        view.displayEditOrderHeader();

        //Get order date - Check if any orders for date, otherwise shortcut out.
        LocalDate orderDate = view.askForOrderDate();
        if (service.getOrdersByDate(orderDate).isEmpty()) {
            view.displayNoOrdersOnDateMessage(orderDate);
            return;
        }

        //Get order by order number - If not exist, shortcut out
        Order order;
        int orderNumber = view.askForOrderNumber();
        try {
            order = service.getOrder(orderDate, orderNumber);
        } catch (NoSuchOrderException e) {
            view.displayNoSuchOrderMessage();
            return;
        }

        //Get updated product types and tax states
        Map<String, Product> productTypes = service.getAllProductsIndexedByProductType();
        Map<String, Tax> taxStates = service.getAllTaxesIndexedByState();

        //Create order copy to edit (to not mutate the original order object).
        Order orderCopy = new Order(order, order.getOrderNumber());

        //Take order edits (mutate the order copy) - shortcut out if no edits made.
        EditIntensity editIntensity = view.getOrderEdits(orderCopy, productTypes, taxStates);
        if (editIntensity == EditIntensity.NO_EDITS_MADE) {
            view.displayNoEditsMadeMessage();
            return;
        }

        //Re-calculate if necessary
        if (editIntensity == EditIntensity.RECALCULATIONS_REQUIRED) {
            orderCopy = service.calculateOrderCosts(orderCopy);
        }

        //Ask for confirmation to edit order.
        if (view.confirmEditOrder(orderCopy)) {
            try {
                service.editOrder(orderDate, orderCopy);
                view.displayEditOrderCompletedMessage();

            } catch (NoSuchOrderException e) {
                //This shouldn't happen in this implementation, hence a warning.
                view.displayEditOrderLostWarning();
            }
        } else {
            view.displayOperationCancelledMessage();
        }
    }

    private void removeOrderRoutine() {
        view.displayRemoveOrderHeader();

        //Get order date - Check if any orders for date, otherwise shortcut out.
        LocalDate orderDate = view.askForOrderDate();
        if (service.getOrdersByDate(orderDate).isEmpty()) {
            view.displayNoOrdersOnDateMessage(orderDate);
            return;
        }

        //Get order by order number - If not exist, shortcut out
        Order order;
        int orderNumber = view.askForOrderNumber();
        try {
            order = service.getOrder(orderDate, orderNumber);
        } catch (NoSuchOrderException e) {
            view.displayNoSuchOrderMessage();
            return;
        }

        //Ask for confirmation to remove order.
        if (view.confirmRemoveOrder(order)) {
            try {
                service.removeOrder(orderDate, orderNumber);
                view.displayRemoveOrderCompletedMessage();

            } catch (NoSuchOrderException e) {
                //This shouldn't happen in this implementation, hence a warning.
                view.displayRemoveOrderLostWarning();
            }
        } else {
            view.displayOperationCancelledMessage();
        }

    }

    private void exportActiveOrders() {
        view.displayExportActiveOrdersHeader();

        //Export active orders and report completion.
        try {
            service.exportActiveOrders();
            view.displayExportActiveOrdersCompleted();
        } catch (PersistenceException e) {
            view.displayExportActiveOrdersFailedWarning();
        }
    }

}
