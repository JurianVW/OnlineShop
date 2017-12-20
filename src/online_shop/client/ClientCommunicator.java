package online_shop.client;

import fontyspublisher.IRemotePropertyListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientCommunicator extends UnicastRemoteObject implements IRemotePropertyListener {
    protected ClientCommunicator(int port) throws RemoteException {
        super(port);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {

    }
}
