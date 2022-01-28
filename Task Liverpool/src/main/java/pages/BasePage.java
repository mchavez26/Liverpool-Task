package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class BasePage {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    protected BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(5,1));
        js = (JavascriptExecutor) getWebDriver();
    }

    protected WebDriver getWebDriver(){
        return webDriver;
    }

    protected WebElement findElement(By locator){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> findElements(By locator){
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    //The following method also finds elements but only after one of the current existing elements disappear (which happens when current page changes)
    //It helps so that the loops wait until the page changes to find elements and start next iteration
    protected List<WebElement> findElementsAfterACurrentElementDisappear(By locator, By existingElementLocator){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(existingElementLocator));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    //The following method also finds elements but only after one expected elements appears (which happens when current page changes) //Not useful in current tests. Please Ignore.
    protected List<WebElement> findElementsAfterAnExpectedElementAppears(By locator, By expectedElementLocator){
        wait.until(ExpectedConditions.presenceOfElementLocated(expectedElementLocator));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void click(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text){
        findElement(locator).sendKeys(text);
    }

    protected String getElementText(By locator){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getText();
    }

    protected String getElementAttribute(WebElement element, String attribute){
            return element.getAttribute(attribute);
    }

    protected boolean isElementDisplayed(By locator){
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
    }

    //This method exists because when I call the isElementDisplayed method from noResultsFound, it returns a NoSuchElementException when element is not displayed.
    protected boolean isElementDisplayedWithTryCatch(By locator){
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    protected void scrollToElement(By locator){
        js.executeScript("arguments[0].scrollIntoView();", findElement(locator));
    }
}
