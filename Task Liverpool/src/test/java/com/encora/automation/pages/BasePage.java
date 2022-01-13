package com.encora.automation.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {

    private WebDriver webDriver;

    BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    protected WebDriver getWebDriver(){
        return webDriver;
    }
}
