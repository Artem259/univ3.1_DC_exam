package main.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTask9 {
    private final ServerRemoteCollection collection;

    public ServerSocketTask9() {
        this.collection = new ServerRemoteCollection();
    }

    public void start(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                Socket socket = server.accept();
                new Thread(new RequestHandler(socket, collection)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new ServerSocketTask9().start(7777);
    }
}
