package online_shop.supplier;

import online_shop.shared.IProduct;

public class Product implements IProduct {
    private String name;
    private Integer id;
    private Double purchasePrice;
    private Integer amount;
    private Integer edition;

    public Product(String name, Double purchasePrice, Integer amount, Integer edition) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.amount = amount;
        this.edition = edition;
    }

    public Product(Integer id, String name, Double purchasePrice, Integer amount, Integer edition) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.amount = amount;
        this.edition = edition;
    }

    public Product(ProductTable productTable) {
        this.name = productTable.getName();
        this.id = productTable.getId();
        this.purchasePrice = productTable.getPurchasePrice();
        this.amount = productTable.getAmount();
        this.edition = productTable.getEdition();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double price) {
        this.purchasePrice = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public void sell(Integer amount) {
        this.amount -= amount;
    }
}
