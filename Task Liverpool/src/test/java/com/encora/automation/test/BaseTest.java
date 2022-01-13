package com.encora.automation.test;

import com.encora.automation.framework.DriverHandler;
import com.encora.automation.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected HomePage homePage;

    BaseTest(){
        homePage = new HomePage(getDriver2());
    }

    private WebDriver getDriver2(){
        return DriverHandler.getWebDriver();
    }

    @BeforeSuite //executed before the tests
    protected void openLiverpoolURL(){
        getDriver2().get("https://www.liverpool.com.mx/tienda/home");
    }

    @AfterSuite
    protected void teardownSuite(){
        DriverHandler.teardown();
        DriverHandler.closeDriver();
    }

}
