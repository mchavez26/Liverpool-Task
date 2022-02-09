package pages;

import dao.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
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
    private By productPriceFromSearchPage = By.cssSelector(".m-product__card .a-card-discount");
    private By rightArrow = By.cssSelector(".page-link .icon-arrow_right");
    private By filterLowerThan$500Button = By.id("variants.prices.sortPrice-100-500");
    public By noResultsText = By.cssSelector(".a-headline__noResults");
    private By firstResult;
    private List<WebElement> pricesList;
    private String sortByText;
    private Product productPreview = new Product();
    private List<Product> productPreviewList = new ArrayList<Product>();

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

    public List<Product> getProductPreviewFromSearchPageList(String afterSortOrFilter, String textFromLabel){
        if (afterSortOrFilter == "afterSort"){
            pricesList = findElementsAfterAttributeChange(productPriceFromSearchPage, sortByDropDownList, "href", sortByText);
        }
        else if (afterSortOrFilter == "afterFilter"){
            pricesList = findElementsAfterTextChanges(productPriceFromSearchPage, numberOfResultsFromUpperRightLabel, textFromLabel);
        }
        else {pricesList = findElements(productPriceFromSearchPage);
        }

        productPreviewList = new ArrayList<Product>();

        for (WebElement prices: pricesList) {
            productPreview = new Product();
            String priceTextFromSearchPage = prices.getText().substring(1).replace(",", "");
            String priceTextFromSearchpageWithPoint = priceTextFromSearchPage.substring(0,priceTextFromSearchPage.length() - 2) + "." + priceTextFromSearchPage.substring(priceTextFromSearchPage.length() - 2);
            productPreview.setProductPrice(new BigDecimal(priceTextFromSearchpageWithPoint));
            productPreviewList.add(productPreview);
        }
        return productPreviewList;
    }

    public List<Product> getMoreValuesForProductPreviewListFromMoreResultsPages(){
        while (clickNextPage()){
            pricesList = findElementsAfterACurrentElementDisappear(productPriceFromSearchPage, firstResult);

            for (WebElement prices: pricesList) {
                productPreview = new Product();
                String priceTextFromSearchPage = prices.getText().substring(1).replace(",", "");
                String priceTextFromSearchpageWithPoint = priceTextFromSearchPage.substring(0,priceTextFromSearchPage.length() - 2) + "." + priceTextFromSearchPage.substring(priceTextFromSearchPage.length() - 2);
                productPreview.setProductPrice(new BigDecimal(priceTextFromSearchpageWithPoint));
                productPreviewList.add(productPreview);
            }
        }
        return productPreviewList;
    }

    public void clickFilterLowerThan$500(){
        findElement(filterLowerThan$500Button).click();
    }
}