package org.lemon.registry.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.lemon.extension.SPI;
import org.lemon.registry.ServiceRegistry;
import org.lemon.registry.zk.util.CuratorUtils;

import java.net.InetSocketAddress;

@Slf4j
@SPI
public class ZkServiceRegistryImpl implements ServiceRegistry {
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNodes(zkClient, servicePath);
    }
}
