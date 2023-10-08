package org.lemon.remoting.transport;

import org.lemon.remoting.dto.RpcRequest;

public interface RpcRequestTransport {
    Object sendRpcRequest(RpcRequest rpcRequest);
}
