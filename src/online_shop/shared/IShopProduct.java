package online_shop.shared;

import java.io.Serializable;

public interface IShopProduct extends Serializable {
    /**
     * @return the price the product is sold for to a client
     */
    Double getPrice();

    /**
     * @return the description of the product
     */
    String getDescription();
}
