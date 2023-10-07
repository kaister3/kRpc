package org.lemon.remoting.transport.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloServer {
    private static final Logger logger =  LoggerFactory.getLogger(HelloServer.class);

    public static void main(String[] args) {
        // 启动服务
        HelloServer server = new HelloServer();
        server.start(6666);
    }

    public void start(int port) {
        logger.info("HelloServer starting...");
        // 创建serversocket 并且绑定一个端口
        try (ServerSocket server = new ServerSocket(port);) {
            Socket socket;
//            通过 accept() 监听客户请求
            while ((socket = server.accept()) != null) {
                logger.info("client connected");
                try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    Message message = (Message) objectInputStream.readObject();
                    logger.info("server receive message: " + message.getMessage());
                    // 通过输出流向客户端发送响应信息
                    message.setMessage("new Message");
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                } catch (IOException | ClassNotFoundException e) {
                    logger.error("occur exception: ", e);
                }
            }
        } catch (IOException e) {
            logger.error("occur IOException: ", e);
        }
    }
}
