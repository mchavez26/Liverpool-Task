package pages;

import org.openqa.selenium.WebDriver;

public class BasePage {

    private WebDriver webDriver;

    protected BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    protected WebDriver getWebDriver(){
        return webDriver;
    }
}
