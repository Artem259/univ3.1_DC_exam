package main.client;

import java.io.*;
import java.net.Socket;

public class ClientSocketTask9 {
    ClientRemoteCollection collection;
    public void start(String ip, int port) {
        try (Socket socket = new Socket(ip, port)) {
            this.collection = new ClientRemoteCollection(socket);
            new ClientTest(collection).start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new ClientSocketTask9().start("localhost", 7777);
    }
}
