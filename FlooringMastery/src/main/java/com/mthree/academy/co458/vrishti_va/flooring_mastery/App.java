package com.mthree.academy.co458.vrishti_va.flooring_mastery;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.controller.MainController;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.OrderDao;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.OrderDaoFileImpl;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.service.MainService;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.service.MainServiceImpl;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.view.MainView;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.view.UserIOConsoleImpl;

public class App {

    public static void main(String[] args) {

        //Temporarily Here: Wiring Project Components
        MainView mainView = new MainView(new UserIOConsoleImpl());
        OrderDao orderDao = new OrderDaoFileImpl();
        MainService mainService = new MainServiceImpl(orderDao);
        MainController mainController = new MainController(mainView, mainService);

        //Call Main Method
        mainController.run();
    }
}
