package org.lemon.registry;

import org.junit.jupiter.api.Test;
import org.lemon.registry.zk.ZkServiceRegistryImpl;

import java.net.InetSocketAddress;

public class ZkServiceRegistryImplTest {
    @Test
    void should_register_service_successful_and_lookup_servuce_by_service_name() {
        ServiceRegistry zkServiceRegistry = new ZkServiceRegistryImpl();
        InetSocketAddress givenInetSocketAddress = new InetSocketAddress("127.0.0.1", 9333);
    }
}
