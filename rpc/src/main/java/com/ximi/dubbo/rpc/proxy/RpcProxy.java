package com.ximi.dubbo.rpc.proxy;

import com.ximi.dubbo.rpc.Constants;
import com.ximi.dubbo.rpc.protocol.Header;
import com.ximi.dubbo.rpc.protocol.Message;
import com.ximi.dubbo.rpc.protocol.Request;
import com.ximi.dubbo.rpc.protocol.Response;
import com.ximi.dubbo.rpc.registry.Registry;
import com.ximi.dubbo.rpc.registry.ServiceInfo;
import com.ximi.dubbo.rpc.transport.Connection;
import com.ximi.dubbo.rpc.transport.NettyClient;
import com.ximi.dubbo.rpc.transport.NettyResponseFuture;
import io.netty.channel.ChannelFuture;
import org.apache.curator.x.discovery.ServiceInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 客户端RPA 代理
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class RpcProxy implements InvocationHandler {

    private String serviceName;
    private Registry<ServiceInfo> registry;

    public RpcProxy(String serviceName, Registry<ServiceInfo> registry) {
        this.serviceName = serviceName;
        this.registry = registry;
    }

    public static <T> T newProxy(Class<T> clazz, Registry<ServiceInfo> registry) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, new RpcProxy(clazz.getName(), registry));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        List<ServiceInstance<ServiceInfo>> serviceInstances = registry.getServiceInstance("testRpc");
        //随机获取一个实例
        ServiceInstance<ServiceInfo> serviceInstance = serviceInstances.get(ThreadLocalRandom.current().nextInt(serviceInstances.size()));

        String methodName = method.getName();

        Header header = new Header(Constants.magic, Constants.version);
        header.setIsRequest((byte) 1);
        Request request = new Request(serviceName, methodName, args);
        Message<Request> message = new Message<>(header, request);
        return remoteCall(serviceInstance.getPayload(), message);
    }

    private Object remoteCall(ServiceInfo serviceInfo, Message message) throws Exception {
        NettyClient client = new NettyClient(serviceInfo.host, serviceInfo.getPort());
        ChannelFuture channelFuture = client.connect().awaitUninterruptibly();
        Connection connection = new Connection(channelFuture);
        NettyResponseFuture responseFuture = connection.request(message, -1);
        Response response = (Response) responseFuture.getPromise().get();
        if(response.getCode() == 200){
            return response.getResult();
        }else {
            return null;
        }
    }
}
