package org.lemon.remoting.transport;

import org.lemon.extension.SPI;
import org.lemon.remoting.dto.RpcRequest;

@SPI
public interface RpcRequestTransport {
    Object sendRpcRequest(RpcRequest rpcRequest);
}
