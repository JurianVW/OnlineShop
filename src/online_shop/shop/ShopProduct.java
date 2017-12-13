package online_shop.shop;

import online_shop.supplier.Product;

public class ShopProduct extends Product {
    private Double price;
    private String description;

    public ShopProduct(Double price, String description){

    }

    public Double getPrice() {
        throw new UnsupportedOperationException();
    }

    public void setPrice(Double price){
        throw new UnsupportedOperationException();
    }

    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    public void setDescription(String description){
        throw new UnsupportedOperationException();
    }
}
