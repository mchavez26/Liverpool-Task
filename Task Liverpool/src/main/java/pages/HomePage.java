package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By searchBar = By.id("mainSearchbar");
    private By searchBarButton = By.cssSelector(".input-group-text");

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void searchItem(String textToSearch){
        click(searchBar);
        type(searchBar, textToSearch);
        click(searchBarButton);
    }
}