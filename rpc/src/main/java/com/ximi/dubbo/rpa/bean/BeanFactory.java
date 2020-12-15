package com.ximi.dubbo.rpa.bean;

/**
 * bean 工厂
 *
 * @author Ximi
 * @since 2020/12/15
 */
public interface BeanFactory {

    /**
     * 注册bean
     *
     * @param beanName bean 的名称
     * @param bean     bean 的实例
     */
    public void registerBean(String beanName, Object bean);

    /**
     * 根据bean 的名称获取bean
     *
     * @param beanName bean 的名称
     * @return
     */
    public Object getBean(String beanName);

}
