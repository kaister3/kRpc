package org.lemon;

import lombok.extern.slf4j.Slf4j;
import org.lemon.annotation.RpcService;

@Slf4j
@RpcService(group = "test1", version = "version1")
public class DemoRpcServiceImpl implements DemoRpcService {
    @Override
    public String hello() {
        return "hello";
    }
}
