package org.lemon.registry.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.lemon.RpcErrorMessageEnum;
import org.lemon.exception.RpcException;
import org.lemon.loadbalance.LoadBalance;
import org.lemon.loadbalance.loadBalancer.RandomLoadBalance;
import org.lemon.registry.ServiceDiscovery;
import org.lemon.registry.zk.util.CuratorUtils;
import org.lemon.remoting.dto.RpcRequest;
import org.lemon.utils.CollectionUtil;

import java.net.InetSocketAddress;
import java.util.List;

@Slf4j
public class ZkSeviceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkSeviceDiscoveryImpl() {
        this.loadBalance = new RandomLoadBalance();
    }
    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceAddressList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (CollectionUtil.isEmpty(serviceAddressList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CANNOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceAddressList, rpcRequest);
        log.info("Successfully found the serivce adress: [{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port =Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
