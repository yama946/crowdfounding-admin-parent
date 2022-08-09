package com.yama.crowd.util;

/**
 * 规范ajax请求返回json的统一格式，分布式架构中也可以用来调用各个模块时返回统一类型
 */
public class ResultUtil<T> {
    //用来封装当前请求的结果是成功还是失败
    private boolean result;

    //请求失败时，返回的错误信息
    private String message;

    //要返回的数据对象
    private T data;

    public static ResultUtil fail(String exceptionMessage) {
        return null;
    }
}
