package online_shop.shop;

import fontyspublisher.RemotePublisher;
import online_shop.shared.Account;
import online_shop.shared.IShop;
import online_shop.supplier.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Shop extends UnicastRemoteObject implements IShop {
    private ShopFX shopFX;

    private RemotePublisher remotePublisher;

    private ShopCommunicator shopCommunicator;

    private List<ShopProduct> shopProducts;

    private Account account;

    private DatabaseShop database;

    public Shop(ShopFX shopFX) throws RemoteException {
        this.shopFX = shopFX;
        database = new DatabaseShop();
        shopCommunicator = new ShopCommunicator(this);
        updateSupplierProducts(shopCommunicator.getSupplierProducts());
    }

    public List<ShopProduct> getShopProducts() {
          return database.getShopProducts();
    }

    public void updateSupplierProducts(List<Product> products){
       database.updateSupplierProducts(products);
    }

    public void addShopProduct(Product product){
        database.addShopProduct(new ShopProduct(product));
        updateFX();
    }

    public void shopProductChanged(ShopProduct product){
        database.updateShopProduct(product);
    }

    public void productChanged(Product product){
        database.updateProduct(product);
        updateFX();
    }

    public void orderProducts(List<ShopProduct> shopProducts, Integer accountId, String session) {
        throw new UnsupportedOperationException();
    }

    public boolean logIn(String username, String password) {
        Account a = database.logIn(username, password);
        if (a != null) {
            this.account = a;
            return true;
        }
        return false;
    }

    public void logOut(String session) {
        throw new UnsupportedOperationException();
    }

    public Account register(String name, String email, String streetname, String houseNumber, String postalCode, String place) {
        throw new UnsupportedOperationException();
    }

    private void updateFX(){
        shopFX.update();
    }
}
