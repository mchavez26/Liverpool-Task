package pages;

import dao.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductPage extends BasePage{
    private By productNameFromProductPage = By.cssSelector(".o-product__description .a-product__information--title");
    private By productDescriptionFromProductPage = By.cssSelector(".a-product__paragraphProductDescriptionContentWeb");
    private By productPriceFromProductPage = By.cssSelector(".o-product__description .a-product__paragraphDiscountPrice");
    private By productImagesFromProductPage = By.cssSelector("#listBeforeEtalage .img-viewer img");

    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public Product getProductInfo(){
        Product product = new Product();
        product.setProductName(getTextFromProductName());
        product.setProductDescription(getTextFromProductDescription());
        product.setProductPrice(getProductPriceBigDecimal());
        product.setImages(getURLFromProductImages());
        return product;
    }

    public String getTextFromProductName(){
        return getElementText(productNameFromProductPage);
    }

    public String getTextFromProductDescription(){
        return getElementText(productDescriptionFromProductPage);
    }

    public BigDecimal getProductPriceBigDecimal(){
        String priceTextFromProductPage = getElementText(productPriceFromProductPage).substring(1).replace("\n",".").replace(",", "");
        return new BigDecimal(priceTextFromProductPage);
    }

    public List<String> getURLFromProductImages(){
        List<WebElement> productImagesList = findElements(productImagesFromProductPage);
        List<String> urlList = new ArrayList<>();

        for (WebElement Urls: productImagesList) {
            urlList.add(getElementAttribute(Urls, "src"));
        }
        return urlList;
    }
}