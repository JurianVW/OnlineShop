package online_shop.supplier;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty id;
    private final SimpleDoubleProperty purchasePrice;
    private final SimpleIntegerProperty amount;
    private final SimpleIntegerProperty edition;

    public Product(String name, Integer id, Double purchasePrice, Integer amount, Integer edition) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleIntegerProperty(id);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.amount = new SimpleIntegerProperty(amount);
        this.edition = new SimpleIntegerProperty(edition);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name){
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

    public void sell(Integer amount) {
        throw new UnsupportedOperationException();
        // this.amount -= amount;
    }
}
