package org.lemon.compress;

import org.lemon.extension.SPI;

@SPI
public interface Compress {
    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);
}
