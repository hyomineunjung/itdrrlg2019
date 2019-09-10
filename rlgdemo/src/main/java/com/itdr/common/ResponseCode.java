package com.itdr.common;

import org.springframework.stereotype.Component;

@Component
public class ResponseCode<T> {
    private Integer status;
    private T data;
    private String mag;

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

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "status=" + status +
                ", data=" + data +
                ", mag='" + mag + '\'' +
                '}';
    }

    //成功时候返回状态码和成功获取的数据
    public static <T> ResponseCode successRS(Integer status, T data) {
        ResponseCode rs = new ResponseCode();
        rs.setStatus(status);
        rs.setData(data);
        return rs;
    }

    //失败时返回状态码和错误信息
    public static ResponseCode defeatedRS(Integer status, String msg) {
        ResponseCode rs = new ResponseCode();
        rs.setStatus(status);
        rs.setMag(msg);
        return rs;
    }

}
