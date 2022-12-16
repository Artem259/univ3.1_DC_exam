package main.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRmiTask9 {
    private final ServerRemoteCollection collection;

    public ServerRmiTask9() throws RemoteException {
        this.collection = new ServerRemoteCollection();
    }

    public void start(int port) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("Products", collection);
    }

    public static void main(String[] args) throws RemoteException {
        new ServerRmiTask9().start(7777);
    }
}
