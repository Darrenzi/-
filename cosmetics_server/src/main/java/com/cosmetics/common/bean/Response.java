package com.cosmetics.common.bean;

import lombok.Data;

@Data
public class Response<T> {
    Integer code;
    String desc;
    T data;

    public static <T> Response<T> success(T data){
        Response<T> res = new Response<>();
        res.code = 1;
        res.desc = "操作成功";
        res.data = data;
        return res;
    }

    public static <T> Response<T> fail(String desc){
        Response<T> res = new Response<>();
        res.code = 0;
        res.desc = desc;
        return res;
    }

    public static <T> Response<T> fail(String desc, T data){
        Response<T> res = new Response<>();
        res.code = 0;
        res.desc = desc;
        res.data = data;
        return res;
    }

    public static <T> Response<T> error(String desc){
        Response<T> res = new Response<>();
        res.code = -1;
        res.desc = desc;
        return res;
    }
}
