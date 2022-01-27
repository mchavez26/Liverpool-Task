package com.encora.automation.test;

import framework.DriverHandler;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    protected WebDriver getDriver(){
        return DriverHandler.getWebDriver();
    }

    @BeforeTest
    protected void setBrowser(){
        getDriver().manage().window().maximize();
    }

    @BeforeMethod
    protected void openLiverpoolURL(){
        getDriver().get("https://www.liverpool.com.mx/tienda/home");
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
