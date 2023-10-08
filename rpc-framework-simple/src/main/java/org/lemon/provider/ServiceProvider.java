package org.lemon.provider;

import org.lemon.annotation.RpcService;
import org.lemon.config.RpcServiceConfig;

public interface ServiceProvider {
    void addService(RpcServiceConfig rpcServiceConfig);

    Object getService(String rpcServiceName);

    void publishService(RpcServiceConfig rpcServiceConfig);
}
