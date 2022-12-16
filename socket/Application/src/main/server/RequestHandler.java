package main.server;

import main.common.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private final ServerRemoteCollection collection;

    private final Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public RequestHandler(Socket socket, ServerRemoteCollection collection) throws IOException {
        this.collection = collection;
        this.socket = socket;
    }

    private void clearRequest() throws IOException {
        collection.clear();
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(0);
    }

    private void getNextProductIdRequest() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(collection.getNextProductId());
    }

    private void getProductByIdRequest() throws IOException, ClassNotFoundException {
        int id = (Integer) in.readObject();

        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(collection.getProductById(id));
    }

    private void addProductRequest() throws IOException, ClassNotFoundException {
        Product product = (Product) in.readObject();

        out = new ObjectOutputStream(socket.getOutputStream());
        try {
            collection.addProduct(product);
        } catch (Exception e) {
            out.writeObject(1);
            return;
        }
        out.writeObject(0);
    }

    private void deleteProductByIdRequest() throws IOException, ClassNotFoundException {
        int id = (Integer) in.readObject();

        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(collection.deleteProductById(id));
    }

    private void productsByNameRequest() throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();

        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(collection.productsByName(name));
    }

    private void productsByNameAndMaxPriceRequest() throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        int maxPrice = (Integer) in.readObject();

        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(collection.productsByNameAndMaxPrice(name, maxPrice));
    }

    private void productsByMinStoragePeriodRequest() throws IOException, ClassNotFoundException {
        int minStoragePeriod = (Integer) in.readObject();

        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(collection.productsByMinStoragePeriod(minStoragePeriod));
    }

    private void allProductsRequest() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(collection.allProducts());
    }

    @Override
    public void run() {
        while (true) {
            try {
                in = new ObjectInputStream(socket.getInputStream());
                int requestCode = (Integer) in.readObject();
                switch (requestCode) {
                    case 1 -> clearRequest();
                    case 2 -> getNextProductIdRequest();
                    case 3 -> getProductByIdRequest();
                    case 4 -> addProductRequest();
                    case 5 -> deleteProductByIdRequest();
                    case 6 -> productsByNameRequest();
                    case 7 -> productsByNameAndMaxPriceRequest();
                    case 8 -> productsByMinStoragePeriodRequest();
                    case 9 -> allProductsRequest();
                    default -> throw new IOException();
                }
            } catch (IOException | ClassNotFoundException e) {
                break;
            }
        }
        System.out.println("> CLOSED <");
    }
}
