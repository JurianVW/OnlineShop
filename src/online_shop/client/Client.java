package online_shop.client;

import online_shop.shared.IShopProduct;
import online_shop.shop.ShopProduct;

import java.rmi.RemoteException;
import java.util.List;

public class Client {
    private ClientFX clientFX;

    private ClientCommunicator clientCommunicator;

    private List<ShopProduct> cart;

    private String session;

    public Client(ClientFX clientFX) throws RemoteException {
        this.clientFX = clientFX;
        clientCommunicator = new ClientCommunicator(this);
    }

    public String logIn(String username, String password) {
        try {
            this.session = clientCommunicator.logIn(username, password);
            return session;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String register(String name, String email, String password, String streetname, String houseNumber, String postalCode, String place) {
        try {
            return clientCommunicator.register(name, email, password, streetname, houseNumber, postalCode, place);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addToCart(ShopProduct product, int amount) {
        for (int i = 0; i < amount; i++) {
            cart.add(product);
        }
    }

    public void removeFromCart(IShopProduct product, int amount) {
        for (int i = 0; i < amount; i++) {
            cart.remove(cart.indexOf(product));
        }
    }

    public void orderProducts() {
        throw new UnsupportedOperationException();
    }

    public List<ShopProduct> getShopProducts() {
        try {
            return clientCommunicator.getShopProducts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changedShopProduct(ShopProduct shopProduct) {
        updateFX();
    }

    public void removedShopProduct(ShopProduct shopProduct) {
        updateFX();
    }

    public void newShopProduct(ShopProduct shopProduct) {
        updateFX();
    }

    private void updateFX() {
        clientFX.update();
    }
}
