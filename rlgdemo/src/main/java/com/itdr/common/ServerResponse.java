package com.itdr.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private Integer status;
    private T data;
    private String msg;

    //获取成功的对象,成功状态码和数据
    private ServerResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    //获取成功的对象,成功数据
    private ServerResponse(T data) {
        this.status = 200;
        this.data = data;
    }

    //获取成功的对象，成功状态码，数据，信息
    private ServerResponse(Integer status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    //获取失败的对象，失败状态码，失败信息
    private ServerResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    //获取失败的对象，失败信息
    private ServerResponse(String msg) {
        this.status = 100;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //成功时候返回状态码和成功获取的数据
    public static <T> ServerResponse successRS(Integer status, T data) {
        return new ServerResponse(status, data);
    }

    //成功时候只传入数据
    public static <T> ServerResponse successRS(T data) {
        return new ServerResponse(data);
    }

    //成功时候传入状态码，数据，信息
    public static <T> ServerResponse successRS(Integer status, T data, String msg) {
        return new ServerResponse(status, data, msg);
    }

    //成功时候传入状态码，信息
    public static <T> ServerResponse successRS(Integer status, String msg) {
        return new ServerResponse(status, msg);
    }

    //失败时候返回状态码和成功获取的数据
    public static <T> ServerResponse defeatedRS(Integer status, String msg) {
        return new ServerResponse(status, msg);
    }

    //失败时候只传入数据
    public static <T> ServerResponse defeatedRS(String msg) {
        return new ServerResponse(msg);
    }

    //判断是否成功
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == 200;
    }
}
