package main.server;

import main.common.Product;
import main.common.remote.RemoteCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerRemoteCollection implements RemoteCollection {
    private final List<Product> products;

    public ServerRemoteCollection() {
        this.products = new ArrayList<>();
    }

    private Integer indexOfProductById(int id) {
        for (int i=0; i<products.size(); i++) {
            if (products.get(i).getId() == id) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        products.clear();
    }

    @Override
    public Integer getNextProductId() {
        int res = -1;
        for (Product product : products) {
            res = Math.max(res, product.getId());
        }
        return res + 1;
    }

    @Override
    public Product getProductById(int id) {
        Integer i = indexOfProductById(id);
        if (i == null) {
            return null;
        }
        return products.get(i);
    }

    @Override
    public void addProduct(Product product) {
        if (indexOfProductById(product.getId()) != null) {
            throw new IllegalArgumentException();
        }
        products.add(product);
    }

    @Override
    public Boolean deleteProductById(int id) {
        Integer i = indexOfProductById(id);
        if (i == null) {
            return false;
        }
        products.remove(i.intValue());
        return true;
    }

    @Override
    public List<Product> productsByName(String name) {
        return products.stream().filter(product -> product.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> productsByNameAndMaxPrice(String name, int maxPrice) {
        return products.stream().filter(product -> (product.getName().equals(name) && product.getPrice() <= maxPrice))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> productsByMinStoragePeriod(int storagePeriod) {
        return products.stream().filter(product -> product.getStoragePeriod() >= storagePeriod)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> allProducts() {
        return products;
    }
}
