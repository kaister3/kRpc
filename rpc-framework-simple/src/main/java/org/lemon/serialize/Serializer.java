package org.lemon.serialize;

import org.lemon.extension.SPI;

@SPI
public interface Serializer {
    byte[] serializer(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
