package com.ximi.dubbo.rpc.protocol;

import java.io.Serializable;

/**
 * 消息请求
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class Request implements Serializable {

    //服务名,类名
    private String serviceName;
    //方法名称
    private String methodName;
    //参数类型
    private Class<?>[] parameterType;
    //参数值
    private Object[] parameterValue;

    public Request(String serviceName, String methodName, Object[] parameterValue) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.parameterValue = parameterValue;
        parameterType = new Class<?>[parameterValue.length];
        for (int index = 0; index < parameterValue.length; index++) {
            parameterType[index] = parameterValue[index].getClass();
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?>[] parameterType) {
        this.parameterType = parameterType;
    }

    public Object[] getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Object[] parameterValue) {
        this.parameterValue = parameterValue;
    }
}
