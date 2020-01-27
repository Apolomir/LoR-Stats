package com.rybalko.ua.lorstats.controller;

import com.rybalko.ua.lorstats.app.ResponseHandler;
import com.rybalko.ua.lorstats.view.ViewBuilder;

public class MainController {

    public static void main(String[] args) {
        new ResponseHandler(new ViewBuilder()).execute();
    }

}
