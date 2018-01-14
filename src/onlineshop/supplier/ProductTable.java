package onlineshop.supplier;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import onlineshop.shared.IProduct;

public class ProductTable implements IProduct {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty id;
    private final SimpleDoubleProperty purchasePrice;
    private final SimpleIntegerProperty amount;
    private final SimpleIntegerProperty edition;
    private Product product;

    public ProductTable(Product product) {
        this.name = new SimpleStringProperty(product.getName());
        this.id = new SimpleIntegerProperty(product.getId());
        this.purchasePrice = new SimpleDoubleProperty(product.getPurchasePrice());
        this.amount = new SimpleIntegerProperty(product.getAmount());
        this.edition = new SimpleIntegerProperty(product.getEdition());
        this.product = product;
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

    public Product getProduct() {
        return this.product;
    }
}
