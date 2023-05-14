package com.example.entity;

import lombok.Data;

/**
 * @ClassName RestBean
 * @Description 统一结果返回类
 * @Author su
 * @Date 2023/4/19 12:46
 */
@Data

public class RestBean<T> {

    private int status;

    private boolean success;

    private T message;

    private RestBean(int status, boolean success, T message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public static <T> RestBean<T> success(){
        return new RestBean<>(200, true, null);
    }

    public static <T> RestBean<T> success(T data){
        return new RestBean<>(200, true, data);
    }

    public static <T> RestBean<T> failure(int status){
        return new RestBean<>(status, false, null);
    }

    public static <T> RestBean<T> failure(int status, T data){
        return new RestBean<>(status, false, data);
    }



}
