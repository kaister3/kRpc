package org.lemon.serialize;

public interface Serializer {
    byte[] serializer(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
