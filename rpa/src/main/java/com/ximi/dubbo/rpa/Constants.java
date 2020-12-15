package com.ximi.dubbo.rpa;

import com.ximi.dubbo.rpa.transport.NettyResponseFuture;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class Constants {

    //魔数
    public static Integer magic = 190321;

    //版本
    public static byte version = 1;

    //头部的长度
    public static Integer headerLength = 18;

    public static Integer heartCode = -1;

}
