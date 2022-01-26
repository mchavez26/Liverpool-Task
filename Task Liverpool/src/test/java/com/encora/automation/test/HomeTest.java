package com.encora.automation.test;

import dao.Product;
import org.testng.Assert;
import pages.HomePage;
import pages.ProductPage;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class HomeTest extends BaseTest{

    private HomePage homePage;
    private ProductPage productPage;
    private static Logger logger;
    private Product productInfo;

    private HomeTest(){
        homePage = new HomePage(getDriver());
        productPage = new ProductPage(getDriver());
        logger = Logger.getLogger(HomeTest.class.getName());
    }

    @Test
    //Validates that when the user searches for a specific product (1103656130), the expected elements are displayed in the product page (name, description, price and images).
    private void tc1_SearchWithCode (){
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

}
