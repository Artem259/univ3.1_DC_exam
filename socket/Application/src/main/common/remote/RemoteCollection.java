package main.common.remote;

import main.common.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface RemoteCollection {
    void clear() throws IOException, ClassNotFoundException;

    Integer getNextProductId() throws IOException, ClassNotFoundException;

    Product getProductById(int id) throws IOException, ClassNotFoundException;

    void addProduct(Product product) throws IOException, ClassNotFoundException;

    Boolean deleteProductById(int id) throws IOException, ClassNotFoundException;

    List<Product> productsByName(String name) throws IOException, ClassNotFoundException;

    List<Product> productsByNameAndMaxPrice(String name, int maxPrice) throws IOException, ClassNotFoundException;

    List<Product> productsByMinStoragePeriod(int storagePeriod) throws IOException, ClassNotFoundException;

    List<Product> allProducts() throws IOException, ClassNotFoundException;
}
