package dao;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private List<String> images;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Product{ \n" +
                "Product Name = '" + productName + '\'' +
                ",\nProduct Description = '" + productDescription + '\'' +
                ",\nProduct Price = '$" + productPrice + '\'' +
                ",\nImages = " + images +
                '}';
    }
}
