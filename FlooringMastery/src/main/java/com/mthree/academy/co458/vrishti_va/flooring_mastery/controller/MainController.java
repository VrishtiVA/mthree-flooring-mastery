package com.mthree.academy.co458.vrishti_va.flooring_mastery.controller;

import com.mthree.academy.co458.vrishti_va.flooring_mastery.service.MainService;
import com.mthree.academy.co458.vrishti_va.flooring_mastery.view.MainView;

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

        } while (keepGoing);

    }

}
