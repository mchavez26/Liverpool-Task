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
    private By filterLowerThan$500Button = By.id("variants.prices.sortPrice-100-500");
    //private By filterLowerThan$500Button2 = By.xpath("//input[@id='variants.prices.sortPrice-100-500']"); //Please ignore these locators, I am leaving them here for future reference
    //private By filterLowerThan$500Button3 = By.cssSelector("#variants\\.prices\\.sortPrice-100-500"); //Please ignore these locators, I am leaving them here for future reference
    public By noResultsText = By.cssSelector(".a-headline__noResults");
    private By firstResult;
    public List<WebElement> pricesList;
    public List<Integer> pricesIntList;
    private String sortByText;

    public enum SortType {
        RELEVANCE, LOWPRICE, HIGHPRICE, RATING, VIEWED, SOLD
    }

    public SearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public int getNumberOfResultsFromUpperRightLabel(){
        return Integer.parseInt(getElementText(numberOfResultsFromUpperRightLabel));
    }

    //Logic for clicking on the Results Page Right Arrow
    public boolean clickNextPage(){
        if (isElementDisplayed(rightArrow)){
            String productId = getElementAttribute(findElement(searchResultsDisplayed), "data-prodid");
            firstResult = By.cssSelector("li.m-product__card[data-prodid=\"" + productId + "\"]");
            scrollToElement(rightArrow);
            click(rightArrow);
            return true;
        }
        else {
            return false;
        }
    }

    public int getTotalNumberOfResultsDisplayed() {
        int totalResults = findElements(searchResultsDisplayed).size();

        while (clickNextPage()){
            totalResults += findElementsAfterACurrentElementDisappear(searchResultsDisplayed, firstResult).size();
        }
        return totalResults;
    }

    public void clickListViewButton(){
        click(listViewButton);
    }

    public void clickSortProductsBy (SortType sortType) throws InterruptedException {

        click(sortByDropDownList);
        switch (sortType) {
            case RELEVANCE:
                click(sortByRelevance);
                sortByText = "Relevancia";
                break;

            case LOWPRICE:
                click(sortByLowPrice);
                sortByText = "sortPrice|0";
                break;

            case HIGHPRICE:
                click(sortByHighPrice);
                sortByText = "sortPrice|1";
                break;

            case RATING:
                click(sortByRating);
                sortByText = "rating";
                break;

            case VIEWED:
                click(sortByViewed);
                sortByText = "viewed";
                break;

            case SOLD:
                click(sortBySold);
                sortByText = "sold";
                break;
        }
    }

    public void definePricesIntListAfterSorting(){
        pricesList = findElementsAfterAttributeChange(productPrice, sortByDropDownList, "href", sortByText);
        pricesIntList = new ArrayList<>();

        for (WebElement prices: pricesList) {
            pricesIntList.add(Integer.parseInt(prices.getText().substring(1).replace(",", "")));
        }
    }

    public void definePricesIntListAfterFiltering(String textFromLabel){
        pricesList = findElementsAfterTextChanges(productPrice, numberOfResultsFromUpperRightLabel, textFromLabel);
        pricesIntList = new ArrayList<>();

        for (WebElement prices: pricesList) {
            pricesIntList.add(Integer.parseInt(prices.getText().substring(1).replace(",", "")));
        }
    } //I know this includes repeated code from the method above, I'll update it after making sure it works.

    public void addValuesToPricesIntListFromMoreResultsPages(){
        while (clickNextPage()){
            pricesList = findElementsAfterACurrentElementDisappear(productPrice, firstResult);
            for (WebElement prices: pricesList) {
                pricesIntList.add(Integer.parseInt(prices.getText().substring(1).replace(",", "")));
            }
        }
    }

    public void clickFilterLowerThan$500() throws InterruptedException {
        findElement(filterLowerThan$500Button).click();
    }
}