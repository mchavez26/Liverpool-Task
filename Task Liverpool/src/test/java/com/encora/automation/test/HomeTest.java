package com.encora.automation.test;

import pages.HomePage;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest{

    protected HomePage homePage;

    protected HomeTest(){
        homePage = new HomePage(getDriver());
    }

    @Test
    private void TC1_SearchWithCode (){
        System.out.println("test");
    }

}
