package com.mthree.academy.co458.vrishti_va.flooring_mastery;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.controller.MainController;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        //Instantiate main controller with dependencies.
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        MainController mainController = applicationContext.getBean("mainController", MainController.class);

        //Start up the application using controller run method.
        mainController.run();
    }
}
