package com.mthree.academy.co458.vrishti_va.flooring_mastery.controller;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.model.Order;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.service.MainService;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.view.MainView;

import java.time.LocalDate;
import java.util.List;

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
