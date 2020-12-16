package com.ximi.dubbo.rpc.protocol;

import java.io.Serializable;

/**
 * 消息响应
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class Response implements Serializable {

    private Integer code;
    private String errorMessage;
    private Object result;

    public Response() {
    }

    public Response(Integer code, String errorMessage, Object result) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
