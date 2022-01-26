package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage extends BasePage{

    private By numberOfResultsFromUpperRightLabel = By.cssSelector(".a-plp-results-title span");
    private By searchResultsDisplayed = By.cssSelector("li.m-product__card");
    private By numberOfPagesBy = By.xpath("//a[contains(@class, 'page-link') and string-length(text()) > 0]");

    public SearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public int getNumberOfResultsFromUpperRightLabel(){
        return Integer.parseInt(getElementText(numberOfResultsFromUpperRightLabel));
    }

    public int getTotalNumberOfResultsDisplayed() {
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        int totalResults = findElements(searchResultsDisplayed).size();
        List<WebElement> pages = findElements(numberOfPagesBy);
        int lastPage = Integer.parseInt(pages.get(pages.size()-1).getText());

        for (int i = 1; i < lastPage; i++) {
            String productId = getElementAttribute(findElement(searchResultsDisplayed), "data-prodid");
            By firstResult = By.cssSelector("li.m-product__card[ data-prodid=\"" + productId + "\"]");
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            if (i < 4){
            pages.get(i).click();}
            else if (i == lastPage - 1){
                pages.get(4).click();
            }
            else {pages.get(3).click();}

            totalResults += findElementsAfterElementsUpdated(searchResultsDisplayed, firstResult).size();
            lastPage = Integer.parseInt(pages.get(pages.size()-1).getText());
        }
        return totalResults;
    }
}