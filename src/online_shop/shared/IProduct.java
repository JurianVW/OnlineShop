package online_shop.shared;

import java.io.Serializable;

public interface IProduct extends Serializable{
    /**
     *
     * @return the price the product is sold for to a shop
     */
    Double getPurchasePrice();

    /**
     *
     * @return the amount of pieces there are left of this product
     */
    Integer getAmount();

    /**
     *
     * @return the edition number, which is how many there are made of this product
     */
    Integer getEdition();
}
