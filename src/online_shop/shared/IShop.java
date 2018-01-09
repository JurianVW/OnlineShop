package online_shop.shared;

import online_shop.shop.ShopProduct;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IShop extends Remote {
    /**
     *
     * @return the list of all shopProducts of that shop
     */
    List<ShopProduct> getShopProducts()throws RemoteException;

    /**
     *
     * @param shopProducts
     * @param accountId
     * @param session
     */
    void orderProducts(List<ShopProduct> shopProducts, Integer accountId, String session)throws RemoteException;

    /**
     *
     * @param username
     * @param password
     * @return the account that belongs to the user with the given info
     */
    String logIn(String username, String password)throws RemoteException;

    /**
     *
     * @param session
     */
    void logOut(String session)throws RemoteException;

    /**
     *
     * @param name
     * @param email
     * @param streetname
     * @param houseNumber
     * @param postalCode
     * @param place
     * @return the account that is made with the given info
     */
    String register(String name, String email, String password, String streetname, String houseNumber, String postalCode, String place)throws RemoteException;
}
