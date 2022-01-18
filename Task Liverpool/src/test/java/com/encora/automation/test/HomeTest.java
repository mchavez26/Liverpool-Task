package com.encora.automation.test;

import org.testng.Assert;
import pages.HomePage;
import pages.ProductPage;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest{

    private HomePage homePage;
    private ProductPage productPage;

    private HomeTest(){
        homePage = new HomePage(getDriver());
        productPage = new ProductPage(getDriver());
    }

    @Test
    private void TC1_SearchWithCode (){
        homePage.clickSearchBar();
        homePage.typeSearchBar();
        homePage.clickSearchBarButton();
        Assert.assertEquals(productPage.getProductInfo().getProductName() , "Apple MacBook Pro (13 Pulgadas, Chip M1, 8 GB RAM, 256 GB SSD) - Gris Espacial", "Product Name is correct.");
        Assert.assertTrue(productPage.getProductInfo().getProductDescription().contains("El chip M1 de Apple redefine al Macbook Pro de 13 pulgadas."), "Description contains text correctly");
        String priceText = productPage.getProductInfo().getProductPrice();
        String priceText2 = priceText.substring(1,2);
        int price = Integer.parseInt(priceText2);
        Assert.assertTrue((price > 0) && (!priceText.equals("$1.00")), "Price is bigger than 1");
        Assert.assertTrue(productPage.countProductImages() > 1 , "There are more than 1 product images");
        System.out.println(productPage.getProductInfo().toString());
    }

}
