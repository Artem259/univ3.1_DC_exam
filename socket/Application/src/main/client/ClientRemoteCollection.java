package main.client;

import main.common.Product;
import main.common.remote.RemoteCollection;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ClientRemoteCollection implements RemoteCollection {
    private final Socket socket;

    public ClientRemoteCollection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void clear() throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(1);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        if ((Integer) in.readObject() != 0) {
            throw new RuntimeException();
        }
    }

    @Override
    public Integer getNextProductId() throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(2);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (Integer) in.readObject();
    }

    @Override
    public Product getProductById(int id) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(3);
        out.writeObject(id);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (Product) in.readObject();
    }

    @Override
    public void addProduct(Product product) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(4);
        out.writeObject(product);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        if ((Integer) in.readObject() != 0) {
            throw new RuntimeException();
        }
    }

    @Override
    public Boolean deleteProductById(int id) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(5);
        out.writeObject(id);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (Boolean) in.readObject();
    }

    @Override
    public List<Product> productsByName(String name) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(6);
        out.writeObject(name);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (List<Product>) in.readObject();
    }

    @Override
    public List<Product> productsByNameAndMaxPrice(String name, int maxPrice) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(7);
        out.writeObject(name);
        out.writeObject(maxPrice);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (List<Product>) in.readObject();
    }

    @Override
    public List<Product> productsByMinStoragePeriod(int storagePeriod) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(8);
        out.writeObject(storagePeriod);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (List<Product>) in.readObject();
    }

    @Override
    public List<Product> allProducts() throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(9);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (List<Product>) in.readObject();
    }
}
