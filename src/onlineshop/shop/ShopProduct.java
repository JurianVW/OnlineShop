package onlineshop.shop;

import onlineshop.shared.IShopProduct;
import onlineshop.supplier.Product;

public class ShopProduct extends Product implements IShopProduct {
    private Double price;
    private String description;

    public ShopProduct(Product product) {
        super(product.getId(), product.getName(), product.getPurchasePrice(), product.getAmount(), product.getEdition());
        this.price = 0.0;
        this.description = "";
    }

    public ShopProduct(Product product, Double price, String description) {
        super(product.getId(), product.getName(), product.getPurchasePrice(), product.getAmount(), product.getEdition());
        this.price = price;
        this.description = description;
    }

    public ShopProduct(ShopProductTable shopProductTable) {
        super(shopProductTable.getName(), shopProductTable.getPurchasePrice(), shopProductTable.getAmount(), shopProductTable.getEdition());
        this.price = shopProductTable.getPrice();
        this.description = shopProductTable.getDescription();
        this.setId(shopProductTable.getId());
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
