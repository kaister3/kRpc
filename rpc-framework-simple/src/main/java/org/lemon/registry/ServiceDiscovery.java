package org.lemon.registry;

import org.lemon.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
