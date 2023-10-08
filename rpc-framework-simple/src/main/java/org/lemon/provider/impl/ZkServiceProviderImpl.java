package org.lemon.provider.impl;

import org.lemon.config.RpcServiceConfig;
import org.lemon.extension.ExtensionLoader;
import org.lemon.provider.ServiceProvider;
import org.lemon.registry.ServiceRegistry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ZkServiceProviderImpl implements ServiceProvider {

    /**
     * key: rpc service name (interface name + version + group)
     * value: service object
     */
    private final Map<String, Object> serviceMap;
    private final Set<String> registerdService;
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registerdService = ConcurrentHashMap.newKeySet();
        serviceRegistry = ExtensionLoader.
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {

    }

    @Override
    public Object getService(String rpcServiceName) {
        return null;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {

    }
}
