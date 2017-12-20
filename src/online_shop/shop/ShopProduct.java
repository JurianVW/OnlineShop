package online_shop.shop;

import online_shop.supplier.Product;

public class ShopProduct extends Product {
    private Double price;
    private String description;

    public ShopProduct(Product product, Double price, String description){
        super(product.getName(), product.getId(), product.getPurchasePrice(), product.getAmount(), product.getEdition());
        this.price = price;
        this.description = description;
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
