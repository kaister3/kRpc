package org.lemon.loadbalance;

import org.lemon.remoting.dto.RpcRequest;

import java.util.List;

public interface LoadBalance {
    String selectServiceAddress(List<String> serviceAddressList, RpcRequest rpcRequest);
}
