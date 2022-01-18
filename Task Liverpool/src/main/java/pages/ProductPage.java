package pages;

import objects.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends BasePage{

    private By productName = By.cssSelector(".o-product__description .a-product__information--title");
    private By productDescription = By.cssSelector(".a-product__paragraphProductDescriptionContentWeb");
    private By productPrice = By.cssSelector(".o-product__description .a-product__paragraphDiscountPrice");
    private By productImages = By.cssSelector("#listBeforeEtalage .img-viewer img");

    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public Product getProductInfo(){
        Product product = new Product();
        product.setProductName(getTextFromProductName());
        product.setProductDescription(getTextFromProductDescription());
        product.setProductPrice(getTextFromProductPrice());
        product.setImages(getURLFromProductImages());
        return product;
    }

    public String getTextFromProductName(){
        return getElementText(productName);
    }

    public String getTextFromProductDescription(){
        return getElementText(productDescription);
    }

    public String getTextFromProductPrice(){
        return getElementText(productPrice).replace("\n",".");
    }

    public int countProductImages(){
        return getProductInfo().getImages().size();
    }

    public List<String> getURLFromProductImages(){
        List<WebElement> productImagesList = findElements(productImages);
        List<String> urlList = new ArrayList<>();
        for (WebElement Urls: productImagesList
        ) {
            urlList.add(getElementAttributeOfAList(Urls, "src"));
        }
        return urlList;
    }
}