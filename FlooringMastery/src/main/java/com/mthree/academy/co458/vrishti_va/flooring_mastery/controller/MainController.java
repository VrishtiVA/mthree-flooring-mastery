package com.mthree.academy.co458.vrishti_va.flooring_mastery.controller;

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

    private void editOrderRoutine() {
        view.displayEditOrderHeader();


    }

    private void removeOrderRoutine() {
        view.displayRemoveOrderHeader();


    }

    private void exportActiveOrders() {
        view.displayExportActiveOrdersHeader();


    }

}
