package org.lemon.remoting.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.lemon.remoting.dto.RpcRequest;
import org.lemon.remoting.dto.RpcResponse;
import org.lemon.remoting.transport.netty.codec.KryoNettyDecoder;
import org.lemon.remoting.transport.netty.codec.KryoNettyEncoder;
import org.lemon.serialize.kryo.KryoSerializer;

@Slf4j
public class NettyRpcClient {
    private final String host;
    private final int port;
    private static final Bootstrap b;

    public NettyRpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // 初始化相关资源
    static {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        KryoSerializer kryoSerializer = new KryoSerializer();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                // 超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
//                        RpcResponse -> ByteBuf
                        ch.pipeline().addLast(new KryoNettyDecoder(kryoSerializer, RpcResponse.class));
                        // ByteBuf -> RpcRequest
                        ch.pipeline().addLast(new KryoNettyEncoder(kryoSerializer, RpcRequest.class));
                        ch.pipeline().addLast(new NettyRpcClientHandler());
                    }
                });
    }

    /**
     * 发送消息到服务端
     * @param rpcRequest 消息体
     * @return 服务端返回的数据
     */
    public RpcResponse sendMessage(RpcRequest rpcRequest) {
        try {
            ChannelFuture f = b.connect(host, port).sync();
            log.info("client connect to {} ", host + ": " + port);
            Channel futureChannel = f.channel();
            if (futureChannel != null) {
                futureChannel.writeAndFlush(rpcRequest).addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("client sent message: [{}]", rpcRequest.toString());
                    } else {
                        log.error("message sent failed: ", future.cause());
                    }
                });
                // 阻塞等待 直到channel关闭
                futureChannel.closeFuture().sync();
                // 将返回的数据取出
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                return futureChannel.attr(key).get();
            }
        } catch (InterruptedException e) {
            log.error("occur exception when connect server: ", e);
        }
        return null;
    }

    public static void main(String[] args) {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("interface")
                .methodName("hello").build();
        NettyRpcClient nettyRpcClient = new NettyRpcClient("127.0.0.1", 8999);
        for (int i = 0; i < 3; i++) {
            nettyRpcClient.sendMessage(rpcRequest);
        }
        RpcResponse rpcResponse = nettyRpcClient.sendMessage(rpcRequest);
        System.out.println(rpcResponse.toString());
    }
}
