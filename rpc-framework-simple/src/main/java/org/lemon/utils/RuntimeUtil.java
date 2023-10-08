package org.lemon.utils;

public class RuntimeUtil {
    /**
     * get the number of cpu cores
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}
