package com.encora.automation.test;

import framework.DriverHandler;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    protected WebDriver getDriver(){
        return DriverHandler.getWebDriver();
    }

    @BeforeTest
    protected void openLiverpoolURL(){
        getDriver().get("https://www.liverpool.com.mx/tienda/home");
        getDriver().manage().window().maximize();
    }

    @AfterTest
    protected void teardownSuite(){
        DriverHandler.teardown();
    }

    @AfterSuite
    protected void closeDriver(){
        DriverHandler.closeDriver();
    }
}
