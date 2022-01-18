package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    protected BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10,1));
    }

    protected WebDriver getWebDriver(){
        return webDriver;
    }

    protected WebElement findElement(By locator){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return getWebDriver().findElement(locator);
    }

    protected List<WebElement> findElements(By locator){ ///FIX THIS
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return getWebDriver().findElements(locator);
    }

    protected void click(By locator){
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    protected void type(By locator, String text){
        findElement(locator).sendKeys(text);
    }

    protected String getElementText(By locator){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getText();
    }

    protected String getElementAttributeOfAList(WebElement productImageElement, String attribute){
        return productImageElement.getAttribute(attribute);
    }
}
