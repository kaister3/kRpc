package org.lemon.serialize.kryo;

import org.junit.jupiter.api.Test;
import org.lemon.remoting.dto.RpcRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class KryoSerializerTest {

    @Test
    void kryoSerializerTest() {
        RpcRequest target = RpcRequest.builder()
                .methodName("hello")
                .interfaceName("com.lemon.HelloService")
                .paramTypes(new Class<?>[]{String.class, String.class})
                .requestId(UUID.randomUUID().toString())
                .group("group1")
                .version("version1")
                .build();
        KryoSerializer kryoSerializer = new KryoSerializer();
        byte[] bytes = kryoSerializer.serializer(target);
        RpcRequest actual = kryoSerializer.deserialize(bytes, RpcRequest.class);
        assertEquals(target.getGroup(), actual.getGroup());
        assertEquals(target.getVersion(), actual.getVersion());
        assertEquals(target.getRequestId(), actual.getRequestId());
    }
}