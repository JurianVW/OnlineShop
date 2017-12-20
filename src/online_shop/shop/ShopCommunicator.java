package online_shop.shop;

import fontyspublisher.IRemotePropertyListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShopCommunicator extends UnicastRemoteObject implements IRemotePropertyListener {
    protected ShopCommunicator(int port) throws RemoteException {
        super(port);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {

    }
}
