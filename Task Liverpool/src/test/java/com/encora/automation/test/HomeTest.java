package com.encora.automation.test;

import dao.Product;
import org.testng.Assert;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductPage;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class HomeTest extends BaseTest{
    private HomePage homePage;
    private ProductPage productPage;
    private SearchResultsPage searchResultsPage;
    private static Logger logger;
    private Product productInfo;

    private HomeTest(){
        homePage = new HomePage(getDriver());
        productPage = new ProductPage(getDriver());
        searchResultsPage = new SearchResultsPage(getDriver());
        logger = Logger.getLogger(HomeTest.class.getName());
    }

    @Test (description = "Validates that when the user searches for a specific product (1103656130), the expected elements are displayed in the product page (name, description, price and images).")
    public void tc1_SearchWithCode_InfoDisplayedCorrectly (){
        homePage.searchItem("1103656130");
        productInfo = productPage.getProductInfo();
        Assert.assertEquals(productInfo.getProductName() , "Apple MacBook Pro (13 Pulgadas, Chip M1, 8 GB RAM, 256 GB SSD) - Gris Espacial", "Product Name is Incorrect.");
        Assert.assertTrue(productInfo.getProductDescription().contains("El chip M1 de Apple redefine al Macbook Pro de 13 pulgadas."), "Description does Not contain expected text");
        BigDecimal price = productInfo.getProductPrice();
        BigDecimal one = new BigDecimal("1.00");
        Assert.assertTrue(price.compareTo(one) > 0, "Price is Not bigger than 1");
        Assert.assertTrue(productInfo.getImages().size() > 1 , "There are No more than 1 product images");
        logger.info(productInfo.toString());
    }

    @Test (description = "Validates that when searching \"macbook\", the number of results displayed at the upper right corner of the screen matches with the real number of results.")
    public void tc2_SearchMacbook_ResultsDisplayedCorrectly(){
        homePage.searchItem("macbook");
        int numberOfResultsFromLabel = searchResultsPage.getNumberOfResultsFromUpperRightLabel();
        int numberOfResultsDisplayed = searchResultsPage.getTotalNumberOfResultsDisplayed();
        Assert.assertEquals(numberOfResultsFromLabel, numberOfResultsDisplayed, "Number of Results Displayed is Not Correct");
    }

    @Test (description = "Validates that the List View is working correctly and also the sort by price functionality works.")
    public void tc3_SearchMacbook_ListViewAndSortByPrice(){
        homePage.searchItem("macbook");
        searchResultsPage.clickListViewButton();
        searchResultsPage.sortProductsBy(SearchResultsPage.SortType.HIGHPRICE);
        searchResultsPage.checkPriceIsSorted();
        Assert.assertTrue(searchResultsPage.checkPriceIsSorted(), "Prices were Not sorted correctly");
    }
}
