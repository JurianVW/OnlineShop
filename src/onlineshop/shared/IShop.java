package onlineshop.shared;

import onlineshop.shop.ShopProduct;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IShop extends Remote {
    /**
     * @return the list of all shopProducts of that shop
     */
    List<ShopProduct> getShopProducts() throws RemoteException;

    /**
     * @param shopProducts
     * @param accountId
     * @param session
     */
    void orderProducts(List<ShopProduct> shopProducts, Integer accountId, String session) throws RemoteException;

    /**
     * @param username
     * @param password
     * @return the account that belongs to the user with the given info
     */
    Account logIn(String username, String password, String session) throws RemoteException;

    /**
     * @param session
     */
    void logOut(String session) throws RemoteException;

    /**
     * @param name
     * @param email
     * @return the account that is made with the given info
     */
    Boolean register(String name, String email, String password) throws RemoteException;
}
