package online_shop.supplier;

public class Product {
    private Integer id;
    private Double purchasePrice;
    private Integer amount;
    private Integer edition;

    public Product(){
    }

    public Product(Integer id, Double purchasePrice, Integer amount, Integer edition) {

    }

    public Double getPurchasePrice() {
        throw new UnsupportedOperationException();
    }

    public void setPurchasePrice(Double price) {
        throw new UnsupportedOperationException();
    }

    public Integer getAmount() {
        throw new UnsupportedOperationException();
    }

    public void setAmount(Integer amount) {
        throw new UnsupportedOperationException();
    }

    public Integer getEdition() {
        throw new UnsupportedOperationException();
    }

    public void setEdition(Integer edition){
        throw new UnsupportedOperationException();
    }

    public void sell(Integer amount){
        throw new UnsupportedOperationException();
    }
}
