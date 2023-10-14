package org.lemon.loadbalance;

import org.lemon.extension.SPI;
import org.lemon.remoting.dto.RpcRequest;

import java.util.List;

@SPI
public interface LoadBalance {
    String selectServiceAddress(List<String> serviceAddressList, RpcRequest rpcRequest);
}
