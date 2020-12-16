package com.ximi.dubbo.rpc.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例的beanFactory
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class SingletonBeanFactory implements BeanFactory {

    private static SingletonBeanFactory beanFactory = new SingletonBeanFactory();
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    public static BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void registerBean(String beanName, Object bean) {
        beanMap.put(beanName, bean);
    }

    @Override
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}
