package com.encora.automation.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverHandler {

    private static WebDriver webDriver;

    public static WebDriver getWebDriver(){
        if(webDriver == null){
            webDriver = setupDriver();
        }
        return webDriver;
    }

    private static WebDriver setupDriver(){
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().deleteAllCookies();
        return webDriver;
    }

    public static void teardown(){
        if(webDriver != null){
            webDriver.manage().deleteAllCookies();
        }
    }

    public static void closeDriver(){
        if (webDriver != null){
            webDriver.quit();
        }
    }
}
