package onlineshop.shared;

import java.io.Serializable;

public interface IProduct extends Serializable {
    /**
     * @return the price the product is sold for to a shop
     */
    Double getPurchasePrice();

    /**
     * @return the amount of there is in stock
     */
    Integer getAmount();

    /**
     * @return the edition number, which is how many there are made of this product
     */
    Integer getEdition();

    /**
     * @return the name of the product
     */
    String getName();
}
