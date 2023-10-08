package org.lemon.spring;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lemon.annotation.RpcReference;
import org.lemon.annotation.RpcService;
import org.lemon.config.RpcServiceConfig;
import org.lemon.enums.RpcRequestTransportEnum;
import org.lemon.extension.ExtensionLoader;
import org.lemon.factory.SingletonFactory;
import org.lemon.provider.ServiceProvider;
import org.lemon.provider.impl.ZkServiceProviderImpl;
import org.lemon.proxy.RpcClientProxy;
import org.lemon.remoting.transport.RpcRequestTransport;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class SpringBeanPostProcessor implements BeanPostProcessor {

    private final RpcRequestTransport rpcClient;
    private final ServiceProvider serviceProvider;

    public SpringBeanPostProcessor() {
        this.serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
        this.rpcClient = ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).getExtension(RpcRequestTransportEnum.NETTY.getName());
    }

    /**
     * 扫描并装载 RpcService bean
     * @param bean
     * @param beanName
     * @return
     */
    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            log.info("[{}] is annotated with [{}]", bean.getClass().getName(), RpcService.class.getCanonicalName());
            // get rpcService annotation
            RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
            RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                    .group(rpcService.group())
                    .version(rpcService.version())
                    .service(bean).build();
            serviceProvider.publishService(rpcServiceConfig);
        }
        return bean;
    }

    /**
     * 用于在 Bean 初始化完成后进行处理。具体来说，它会检查目标 Bean 中的字段，
     * 如果发现带有 @RpcReference 注解的字段，就为这些字段创建代理对象，并将代理对象注入到目标 Bean 中
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declareField : declaredFields) {
            RpcReference rpcReference = declareField.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                        .group(rpcReference.group())
                        .version(rpcReference.version()).build();
                RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceConfig);
                Object clientProxy = rpcClientProxy.getProxy(declareField.getType());
                declareField.setAccessible(true);
                try {
                    declareField.set(bean, clientProxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
