package online_shop.shop;

import online_shop.shared.Account;
import online_shop.shared.AccountType;
import online_shop.shared.IShop;
import online_shop.supplier.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.List;

public class Shop extends UnicastRemoteObject implements IShop {
    private ShopFX shopFX;

    private ShopCommunicator shopCommunicator;

    private Account account;
    private Hashtable<String, Integer> accountSessions;

    private DatabaseShop database;

    public Shop(ShopFX shopFX, String shopName) throws RemoteException {
        this.shopFX = shopFX;
        database = new DatabaseShop();
        shopCommunicator = new ShopCommunicator(this, shopName);
        updateSupplierProducts(shopCommunicator.getSupplierProducts());
        accountSessions = new Hashtable<>();
    }

    public List<ShopProduct> getShopProducts() {
        return database.getShopProducts();
    }

    public void updateSupplierProducts(List<Product> products) {
        database.updateSupplierProducts(products);
    }

    public void addShopProduct(Product product) {
        ShopProduct shopProduct = new ShopProduct(product);
        database.addShopProduct(shopProduct);
        updateFX();
        shopCommunicator.informNewShopProduct(shopProduct);
    }

    public void shopProductChanged(ShopProduct product) {
        database.updateShopProduct(product);
        shopCommunicator.informChangedShopProduct(product);
    }

    public void productChanged(Product product) {
        database.updateProduct(product);
        shopCommunicator.informChangedShopProduct(database.getShopProduct(product));
        updateFX();
    }

    public void productRemoved(Product product) {
        database.removeSupplierProduct(product);
        shopCommunicator.informRemovedShopProduct(database.getShopProduct(product));
        updateFX();
    }

    public void orderProducts(List<ShopProduct> shopProducts, Integer accountId, String session) {
        if(accountSessions.get(session).equals(accountId)){
            try {
                shopCommunicator.orderProducts(shopProducts);
            }
            catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }

    public Account logIn(String username, String password, String session) {
        Account a = database.logIn(username, password);
        if (a != null) {
          if (a.getAccountType() == AccountType.CUSTOMER) {
                accountSessions.put(session, a.getId());
            }
            return a;
        }
        return null;
    }

    public boolean logInShop(String username, String password) {
        Account a = database.logIn(username, password);
        if (a != null) {
            if (a.getAccountType() == AccountType.SHOPEMPLOYEE) {
                this.account = a;
                return true;
            }
        }
        return false;
    }

    public void logOut(String session) {
        throw new UnsupportedOperationException();
    }

    public String register(String name, String email, String password, String streetname, String houseNumber, String postalCode, String place) {
        throw new UnsupportedOperationException();
    }

    private void updateFX() {
        shopFX.update();
    }
}
