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

    @Test
    //Validates that when the user searches for a specific product (1103656130), the expected elements are displayed in the product page (name, description, price and images).
    private void tc1_SearchWithCode_InfoDisplayedCorrectly (){
        homePage.clickSearchBar();
        homePage.typeSearchBar("1103656130");
        homePage.clickSearchBarButton();
        productInfo = productPage.getProductInfo();
        Assert.assertEquals(productInfo.getProductName() , "Apple MacBook Pro (13 Pulgadas, Chip M1, 8 GB RAM, 256 GB SSD) - Gris Espacial", "Product Name is Incorrect.");
        Assert.assertTrue(productInfo.getProductDescription().contains("El chip M1 de Apple redefine al Macbook Pro de 13 pulgadas."), "Description does Not contain expected text");
        BigDecimal price = productInfo.getProductPrice();
        BigDecimal one = new BigDecimal("1.00");
        Assert.assertTrue(price.compareTo(one) > 0, "Price is Not bigger than 1");
        Assert.assertTrue(productInfo.getImages().size() > 1 , "There are No more than 1 product images");
        logger.info(productInfo.toString());
    }

    @Test
    //Validates that when searching "macbook", the number of results displayed at the upper right corner of the screen matches with the real number of results.
    private void tc2_SearchMacbook_ResultsDisplayedCorrectly(){
        homePage.clickSearchBar();
        homePage.typeSearchBar("macbook");
        homePage.clickSearchBarButton();
        int numberOfResultsFromLabel = searchResultsPage.getNumberOfResultsFromUpperRightLabel();
        int numberOfResultsDisplayed = searchResultsPage.getTotalNumberOfResultsDisplayed();
        Assert.assertEquals(numberOfResultsFromLabel, numberOfResultsDisplayed);
    }
}
