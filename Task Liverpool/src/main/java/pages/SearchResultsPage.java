package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends BasePage{
    private By numberOfResultsFromUpperRightLabel = By.cssSelector(".a-plp-results-title span");
    private By searchResultsDisplayed = By.cssSelector("li.m-product__card");
    private By numberOfPagesBy = By.xpath("//a[contains(@class, 'page-link') and string-length(text()) > 0]");
    private By listViewButton = By.cssSelector(".icon-vistalista");
    private By sortByDropDownList = By.cssSelector(".col-lg-9 #sortby");
    private By sortByPrice = By.cssSelector(".col-lg-9 .dropdown-menu [datahref=\"/tienda/sortPrice|1\"]");
    private By productPrice = By.cssSelector(".m-product__card .a-card-discount");
    private By rightArrow = By.cssSelector(".page-link .icon-arrow_right");

    JavascriptExecutor js = (JavascriptExecutor) getWebDriver();

    public SearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public int getNumberOfResultsFromUpperRightLabel(){
        return Integer.parseInt(getElementText(numberOfResultsFromUpperRightLabel));
    }

    public int getTotalNumberOfResultsDisplayed() {
        int totalResults = findElements(searchResultsDisplayed).size();
        List<WebElement> pages = findElements(numberOfPagesBy);
        int lastPage = Integer.parseInt(pages.get(pages.size()-1).getText());

        //Logic for clicking on the Next Results Page Numbers with a For
        for (int i = 1; i < lastPage; i++) {
            String productId = getElementAttribute(findElement(searchResultsDisplayed), "data-prodid");
            By firstResult = By.cssSelector("li.m-product__card[data-prodid=\"" + productId + "\"]");
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

    public void clickListViewButton(){
        click(listViewButton);
    }

    public void clickSortByPrice(){
        click(sortByDropDownList);
        click(sortByPrice);
    }

    public boolean checkPriceIsSorted(){
        List<WebElement> pricesList = findElements(productPrice);
        List<Integer> pricesIntList = new ArrayList<>();

        for (WebElement prices: pricesList) {
            pricesIntList.add(Integer.parseInt(prices.getText().substring(1).replace(",", "")));
        }

        //Logic for clicking on the Results Page Right Arrow with a While
        while (isElementDisplayed(rightArrow)){
            String productId = getElementAttribute(findElement(searchResultsDisplayed), "data-prodid");
            By firstResult = By.cssSelector("li.m-product__card[data-prodid=\"" + productId + "\"]");
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            click(rightArrow);
            pricesList = findElementsAfterElementsUpdated(productPrice, firstResult);
            for (WebElement prices: pricesList) {
                pricesIntList.add(Integer.parseInt(prices.getText().substring(1).replace(",", "")));
            }
        }

        for (int i = 0; i < pricesIntList.size() - 1; i++){
            boolean compare = (pricesIntList.get(i) >= pricesIntList.get(i + 1));
            if (compare == false){
                return false;
            }
        }
        return true;
    }
}