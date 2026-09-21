package com.mthree.academy.co458.vrishti_va.flooring_mastery;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.controller.MainController;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.dao.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@PropertySource("/application.properties")
public class App {

    public static void main(String[] args) {

        //Instantiate main controller with dependencies.
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("com.mthree.academy.co458.vrishti_va.flooring_mastery");
        applicationContext.refresh();
        MainController mainController = applicationContext.getBean("mainController", MainController.class);

        //Start up the application using controller run method.
        mainController.run();
    }
}
