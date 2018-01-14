package onlineshop.shop;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import onlineshop.shared.IShopProduct;

public class ShopProductTable implements IShopProduct {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty id;
    private final SimpleDoubleProperty purchasePrice;
    private final SimpleIntegerProperty amount;
    private final SimpleIntegerProperty edition;
    private final SimpleDoubleProperty price;
    private final SimpleStringProperty description;
    private ShopProduct shopProduct;

    public ShopProductTable(ShopProduct shopProduct) {
        this.name = new SimpleStringProperty(shopProduct.getName());
        this.id = new SimpleIntegerProperty(shopProduct.getId());
        this.purchasePrice = new SimpleDoubleProperty(shopProduct.getPurchasePrice());
        this.amount = new SimpleIntegerProperty(shopProduct.getAmount());
        this.edition = new SimpleIntegerProperty(shopProduct.getEdition());
        this.price = new SimpleDoubleProperty(shopProduct.getPrice());
        this.description = new SimpleStringProperty(shopProduct.getDescription());
        this.shopProduct = shopProduct;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getId() {
        return id.get();
    }

    public Double getPurchasePrice() {
        return purchasePrice.get();
    }

    public void setPurchasePrice(Double price) {
        this.purchasePrice.set(price);
    }

    public Integer getAmount() {
        return amount.get();
    }

    public void setAmount(Integer amount) {
        this.amount.set(amount);
    }

    public Integer getEdition() {
        return edition.get();
    }

    public void setEdition(Integer edition) {
        this.edition.set(edition);
    }

    public Double getPrice() {
        return price.get();
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public ShopProduct getShopProduct() {
        return this.shopProduct;
    }
}
