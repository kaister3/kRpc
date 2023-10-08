package org.lemon.remoting.transport.socket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.registry.ServiceDiscovery;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@AllArgsConstructor
@Slf4j
public class SocketRpcClient {

    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
//        this.serviceDiscovery = ExtensionLoader.get
    }

    public Object send(Message message, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("occur exception: ", e);
        }
        return null;
    }

    public static void main(String[] args) {
        SocketRpcClient socketRpcClient = new SocketRpcClient();
        Message message1 = new Message("content from client");
        Message message = (Message) socketRpcClient.send(message1, "127.0.0.1", 6666);
        System.out.println("client receive message: " + message.getMessage());
    }
}
