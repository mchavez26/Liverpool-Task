package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends BasePage{
    private By numberOfResultsFromUpperRightLabel = By.cssSelector(".a-plp-results-title span");
    private By searchResultsDisplayed = By.cssSelector("li.m-product__card");
    private By listViewButton = By.cssSelector(".icon-vistalista");
    private By sortByDropDownList = By.cssSelector(".col-lg-9 #sortby");
    private By sortByRelevance = By.cssSelector(".col-lg-9 .dropdown-menu [datahref=\"/tienda/Relevancia|0\"]");
    private By sortByLowPrice = By.cssSelector(".col-lg-9 .dropdown-menu [datahref=\"/tienda/sortPrice|0\"]");
    private By sortByHighPrice = By.cssSelector(".col-lg-9 .dropdown-menu [datahref=\"/tienda/sortPrice|1\"]");
    private By sortByRating = By.cssSelector(".col-lg-9 .dropdown-menu [datahref=\"/tienda/rating|1\"]");
    private By sortByViewed = By.cssSelector(".col-lg-9 .dropdown-menu [datahref=\"/tienda/viewed|1\"]");
    private By sortBySold = By.cssSelector(".col-lg-9 .dropdown-menu [datahref=\"/tienda/sold|1\"]");
    private By productPrice = By.cssSelector(".m-product__card .a-card-discount");
    private By rightArrow = By.cssSelector(".page-link .icon-arrow_right");

    public enum SortType {
        RELEVANCE, LOWPRICE, HIGHPRICE, RATING, VIEWED, SOLD
    }

    public SearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public int getNumberOfResultsFromUpperRightLabel(){
        return Integer.parseInt(getElementText(numberOfResultsFromUpperRightLabel));
    }

    public int getTotalNumberOfResultsDisplayed() {
        int totalResults = findElements(searchResultsDisplayed).size();

        //Logic for clicking on the Results Page Right Arrow with a While
        while (isElementDisplayed(rightArrow)){
            String productId = getElementAttribute(findElement(searchResultsDisplayed), "data-prodid");
            By firstResult = By.cssSelector("li.m-product__card[data-prodid=\"" + productId + "\"]");
            scrollToElement(rightArrow);
            click(rightArrow);
            totalResults += findElementsAfterACurrentElementDisappear(searchResultsDisplayed, firstResult).size();
        }
        return totalResults;
    }

    public void clickListViewButton(){
        click(listViewButton);
    }

    public void sortProductsBy (SortType sortType){

        click(sortByDropDownList);
        switch (sortType) {
            case RELEVANCE:
                click(sortByRelevance);
                break;

            case LOWPRICE:
                click(sortByLowPrice);
                break;

            case HIGHPRICE:
                click(sortByHighPrice);
                break;

            case RATING:
                click(sortByRating);
                break;

            case VIEWED:
                click(sortByViewed);
                break;

            case SOLD:
                click(sortBySold);
                break;
        }
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
            scrollToElement(rightArrow);
            click(rightArrow);
            pricesList = findElementsAfterACurrentElementDisappear(productPrice, firstResult);
            for (WebElement prices: pricesList) {
                pricesIntList.add(Integer.parseInt(prices.getText().substring(1).replace(",", "")));
            }
        }

        for (int i = 0; i < pricesIntList.size() - 1; i++){
            boolean compare = (pricesIntList.get(i) >= pricesIntList.get(i + 1));
            if (!compare){
                return false;
            }
        }
        return true;
    }
}