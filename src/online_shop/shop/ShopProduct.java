package online_shop.shop;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import online_shop.supplier.Product;

public class ShopProduct extends Product {
    private final SimpleDoubleProperty price;
    private final SimpleStringProperty description;

    public ShopProduct(Product product){
        super(product.getName(), product.getId(), product.getPurchasePrice(), product.getAmount(), product.getEdition());
        this.price = new SimpleDoubleProperty(0);
        this.description = new SimpleStringProperty("");
    }

    public ShopProduct(Product product, Double price, String description){
        super(product.getName(), product.getId(), product.getPurchasePrice(), product.getAmount(), product.getEdition());
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
    }

    public Double getPrice() {
      return  price.get();
    }

    public void setPrice(Double price){
        this.price.set(price);
    }

    public String getDescription() {
     return description.get();
    }

    public void setDescription(String description){
       this.description.set(description);
    }
}
