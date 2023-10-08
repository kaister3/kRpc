package org.lemon.loadbalance;

import org.lemon.remoting.dto.RpcRequest;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class AbstractLoadBalancer implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddressList, RpcRequest rpcRequest) {
        if (CollectionUtils.is)
    }
}
