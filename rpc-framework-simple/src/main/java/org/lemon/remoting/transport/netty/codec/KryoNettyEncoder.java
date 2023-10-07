package org.lemon.remoting.transport.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import org.lemon.serialize.Serializer;

/**
 * 自定义编码器
 * 网络传输需要通过字节流来实现
 * ByteBuf是netty提供的字节数据的容器
 */
@AllArgsConstructor
public class KryoNettyEncoder extends MessageToByteEncoder<Object> {
    private final Serializer serializer;
    private final Class<?> genericClass;

    /**
     * 将对象转换为字节码写入bytebuf中
     * @param channelHandlerContext
     * @param o
     * @param byteBuf
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) {
        if (genericClass.isInstance(o)) {
            // 将对象转换为byte
            byte[] body = serializer.serializer(o);
            // 读取消息的长度
            int dataLength = body.length;
            // 写入消息对应的字节数组长度
            byteBuf.writeInt(dataLength);
            // 写入字节数组
            byteBuf.writeBytes(body);
        }
    }
}
