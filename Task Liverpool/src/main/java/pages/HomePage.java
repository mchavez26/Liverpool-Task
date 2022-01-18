package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private By searchBar =By.id("mainSearchbar");
    private By searchBarButton = By.cssSelector(".input-group-text .icon-zoom");

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickSearchBar(){
        click(searchBar);
    }

    public void typeSearchBar(){
        type(searchBar, "1103656130");
    }

    public void clickSearchBarButton(){
        click(searchBarButton);
    }

}
