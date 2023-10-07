package org.lemon.remoting.transport.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.serialize.Serializer;

import java.util.List;

/**
 * 解码器
 */
@AllArgsConstructor
@Slf4j
public class KryoNettyDecoder extends ByteToMessageDecoder {

    private final Serializer serializer;
    private final Class<?> genericClass;

    private static final int BODY_LENGTH = 4;

    /**
     *
     * @param channelHandlerContext ctx 解码器关联的 ChannelHandlerContext 对象
     * @param in 入站数据
     * @param out 解码之后的数据对象需要添加到 out 对象里面
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= BODY_LENGTH) {
            // 标记当前readIndex的位置
            in.markReaderIndex();
            // 读取消息的长度
            int dataLength = in.readInt();
            // 判断不合理的状况
            if (dataLength < 0 || in.readableBytes() < 0) {
                log.error("data or ByteBuf readableBytes is not valid");
                return;
            }
            if (in.readableBytes() < dataLength) {
                in.resetReaderIndex();
                return;
            }
            // 开始序列化
            byte[] body = new byte[dataLength];
            in.readBytes(body);
            Object obj = serializer.deserialize(body, genericClass);
            out.add(obj);
            log.info("successfully decode ByteBuf to Object");
        }
    }
}
