package org.lemon.registry;

import org.junit.jupiter.api.Test;
import org.lemon.DemoRpcService;
import org.lemon.DemoRpcServiceImpl;
import org.lemon.config.RpcServiceConfig;
import org.lemon.registry.zk.ZkServiceDiscoveryImpl;
import org.lemon.registry.zk.ZkServiceRegistryImpl;
import org.lemon.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZkServiceRegistryImplTest {
    @Test
    void should_register_service_successful_and_lookup_servuce_by_service_name() {
        ServiceRegistry zkServiceRegistry = new ZkServiceRegistryImpl();
        InetSocketAddress givenInetSocketAddress = new InetSocketAddress("170.106.147.98", 9333);
        DemoRpcService demoRpcService = new DemoRpcServiceImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2").version("version2").service(demoRpcService).build();
        zkServiceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), givenInetSocketAddress);
        ServiceDiscovery zkServiceDiscovery = new ZkServiceDiscoveryImpl();
        RpcRequest rpcRequest = RpcRequest.builder()
//                .parameters(args)
                .interfaceName(rpcServiceConfig.getServiceName())
//                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceConfig.getGroup())
                .version(rpcServiceConfig.getVersion())
                .build();
        InetSocketAddress acquiredInetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        assertEquals(givenInetSocketAddress.toString(), acquiredInetSocketAddress.toString());
    }
}
