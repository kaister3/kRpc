package org.lemon.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SocketRpcServer {
    public static void main(String[] args) {
        // 启动服务
        SocketRpcServer server = new SocketRpcServer();
        server.start(6666);
    }

    public void start(int port) {
        log.info("HelloServer starting...");
        // 创建serversocket 并且绑定一个端口
        try (ServerSocket server = new ServerSocket(port);) {
            Socket socket;
//            通过 accept() 监听客户请求
            while ((socket = server.accept()) != null) {
                log.info("client connected");
                try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    Message message = (Message) objectInputStream.readObject();
                    log.info("server receive message: " + message.getMessage());
                    // 通过输出流向客户端发送响应信息
                    message.setMessage("new Message");
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                } catch (IOException | ClassNotFoundException e) {
                    log.error("occur exception: ", e);
                }
            }
        } catch (IOException e) {
            log.error("occur IOException: ", e);
        }
    }
}
