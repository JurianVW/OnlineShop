package online_shop.client;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import online_shop.shop.ShopProduct;

public class CartProductTable {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty totalPrice;
    private final SimpleIntegerProperty cartAmount;
    private ShopProduct shopProduct;

    public CartProductTable(ShopProduct shopProduct) {
        this.name = new SimpleStringProperty(shopProduct.getName());
        this.totalPrice = new SimpleDoubleProperty(shopProduct.getPrice());
        this.cartAmount = new SimpleIntegerProperty(1);
        this.shopProduct = shopProduct;
    }

    public void updatePrice() {
        totalPrice.set(shopProduct.getPrice() * cartAmount.get());
    }

    public void updateShopProduct(ShopProduct shopProduct) {
        this.shopProduct = shopProduct;
        this.name.set(shopProduct.getName());
        updatePrice();
    }

    public String getName(){
        return this.name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public Double getTotalPrice(){
        return this.totalPrice.get();
    }

    public void setTotalPrice(Double price){
        this.totalPrice.set(price);
    }

    public Integer getCartAmount(){
        return this.cartAmount.get();
    }

    public void setCartAmount(Integer amount){
        this.cartAmount.set(amount);
        updatePrice();
    }

    public ShopProduct getShopProduct() {
        return shopProduct;
    }
}
