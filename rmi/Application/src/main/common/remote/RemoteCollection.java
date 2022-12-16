package main.common.remote;

import main.common.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteCollection extends Remote {
    void clear() throws RemoteException;

    Integer getNextProductId() throws RemoteException;

    Product getProductById(int id) throws RemoteException;

    void addProduct(Product product) throws RemoteException;

    Boolean deleteProductById(int id) throws RemoteException;

    List<Product> productsByName(String name) throws RemoteException;

    List<Product> productsByNameAndMaxPrice(String name, int maxPrice) throws RemoteException;

    List<Product> productsByMinStoragePeriod(int storagePeriod) throws RemoteException;

    List<Product> allProducts() throws RemoteException;
}
