package main.client;

import main.common.remote.RemoteCollection;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientRmiTask9 {
    RemoteCollection collection;

    public void start(String ip, int port) throws RemoteException, MalformedURLException, NotBoundException {
        String url = "//" + ip + ":" + port + "/Products";
        this.collection = (RemoteCollection) Naming.lookup(url);

        new ClientTest(collection).start();
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        new ClientRmiTask9().start("localhost", 7777);
    }
}
