package com.ximi.dubbo.rpc;

/**
 * 测试用例的实现类
 *
 * @author Ximi
 * @since 2020/12/16
 */
public class DomeServiceImpl implements DomeService {

    @Override
    public String test(String test) {
        System.out.println("rpc client:" + test);
        return "rpc server:" + test;
    }
}
