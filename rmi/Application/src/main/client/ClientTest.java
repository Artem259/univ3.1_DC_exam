package main.client;


import main.common.Product;
import main.common.remote.RemoteCollection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientTest {
    private final RemoteCollection collection;
    private final List<Product> products;

    public ClientTest(RemoteCollection collection) {
        this.collection = collection;
        this.products = new ArrayList<>();
    }

    private void compareLists() throws RemoteException {
        if (!collection.allProducts().equals(products)) {
            throw new RuntimeException();
        }
    }

    private void refill() throws RemoteException {
        products.clear();
        collection.clear();
        Product product;

        // 0
        product = new Product();
        product.setId(collection.getNextProductId());
        product.setName("Name_1");
        product.setUpc("00112345");
        product.setProducer("Producer_1");
        product.setPrice(100);
        product.setStoragePeriod(365);
        product.setQuantity(1);
        products.add(product);
        collection.addProduct(product);

        // 1
        product = new Product();
        product.setId(collection.getNextProductId());
        product.setName("Name_1");
        product.setUpc("87654321");
        product.setProducer("Producer_1");
        product.setPrice(150);
        product.setStoragePeriod(180);
        product.setQuantity(2);
        products.add(product);
        collection.addProduct(product);

        // 2
        product = new Product();
        product.setId(collection.getNextProductId());
        product.setName("Name_1");
        product.setUpc("55554444");
        product.setProducer("Producer_2");
        product.setPrice(175);
        product.setStoragePeriod(270);
        product.setQuantity(4);
        products.add(product);
        collection.addProduct(product);

        // 3
        product = new Product();
        product.setId(collection.getNextProductId());
        product.setName("Name_2");
        product.setUpc("12365478");
        product.setProducer("Producer_2");
        product.setPrice(150);
        product.setStoragePeriod(365);
        product.setQuantity(2);
        products.add(product);
        collection.addProduct(product);

        // 4
        product = new Product();
        product.setId(collection.getNextProductId());
        product.setName("Name_3");
        product.setUpc("12345678");
        product.setProducer("Producer_3");
        product.setPrice(8);
        product.setStoragePeriod(90);
        product.setQuantity(5);
        products.add(product);
        collection.addProduct(product);

        compareLists();
    }

    private void printCollection() throws RemoteException {
        for (Product product : collection.allProducts()) {
            System.out.println(product);
        }
    }

    public void testGetProductById() throws RemoteException {
        for (int i=0; i<products.size(); i++) {
            if (!Objects.equals(collection.getProductById(i), products.get(i))) {
                throw new RuntimeException();
            }
        }
    }

    public void testDeleteProductById() throws RemoteException {
        for (int i=0; i<products.size(); i++) {
            collection.deleteProductById(i);
            products.remove(0);
            if (!Objects.equals(collection.allProducts(), products)) {
                throw new RuntimeException();
            }
        }
    }

    public void testProductsByName() throws RemoteException {
        List<Product> list;

        list = products.subList(0, 3);
        if (!Objects.equals(collection.productsByName("Name_1"), list)) {
            throw new RuntimeException();
        }

        list = products.subList(3, 4);
        if (!Objects.equals(collection.productsByName("Name_2"), list)) {
            throw new RuntimeException();
        }

        list = products.subList(4, 5);
        if (!Objects.equals(collection.productsByName("Name_3"), list)) {
            throw new RuntimeException();
        }
    }

    public void testProductsByNameAndMaxPrice() throws RemoteException {
        List<Product> list;

        list = products.subList(0, 1);
        if (!Objects.equals(collection.productsByNameAndMaxPrice("Name_1", 100), list)) {
            throw new RuntimeException();
        }

        list = products.subList(0, 2);
        if (!Objects.equals(collection.productsByNameAndMaxPrice("Name_1", 150), list)) {
            throw new RuntimeException();
        }

        list = products.subList(0, 3);
        if (!Objects.equals(collection.productsByNameAndMaxPrice("Name_1", 200), list)) {
            throw new RuntimeException();
        }
    }

    public void testProductsByMinStoragePeriod() throws RemoteException {
        List<Product> list = new ArrayList<>();
        list.add(products.get(0));
        list.add(products.get(3));

        if (!Objects.equals(collection.productsByMinStoragePeriod(365), list)) {
            throw new RuntimeException();
        }
    }

    public void start() throws RemoteException {
        refill();
        printCollection();
        testGetProductById();

        refill();
        testDeleteProductById();

        refill();
        testProductsByName();

        refill();
        testProductsByNameAndMaxPrice();

        refill();
        testProductsByMinStoragePeriod();
    }
}
