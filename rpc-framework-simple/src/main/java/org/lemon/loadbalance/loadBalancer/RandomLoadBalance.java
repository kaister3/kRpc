package org.lemon.loadbalance.loadBalancer;

import org.lemon.loadbalance.AbstractLoadBalancer;
import org.lemon.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbstractLoadBalancer {
    @Override
    protected String doSelect(List<String> serviceAddressList, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddressList.get(random.nextInt(serviceAddressList.size()));
    }
}
