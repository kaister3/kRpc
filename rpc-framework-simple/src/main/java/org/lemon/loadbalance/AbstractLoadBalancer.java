package org.lemon.loadbalance;

import org.lemon.remoting.dto.RpcRequest;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class AbstractLoadBalancer implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddressList, RpcRequest rpcRequest) {
        if (CollectionUtils.isEmpty(serviceAddressList)) {
            return null;
        }
        if (serviceAddressList.size() == 1) {
            return serviceAddressList.get(0);
        }
        return doSelect(serviceAddressList, rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddressList, RpcRequest rpcRequest);
}
