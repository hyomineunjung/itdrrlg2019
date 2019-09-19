package com.itdr.common;

public class Const {

    //    成功的状态码
    public static final int SUCESS = 0;

    //    失败的状态码
    public static final int ERROR = 1;

    public interface Cart{
        String LimitQuantitySuccess="LIMIT_NUM_SUCCESS";
        String LimitQuantityFaild="LIMIT_NUM_Faild";
        Integer CHECK=1;
        Integer UNCHECK=0;
    }

    public enum UserEnum {
        NEED_LOGIN(2, "需要登录"),
        NO_LOGIN(101,"用户未登录");

//        状态信息

        private int code;
        private String desc;

        private UserEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    //    常量
    public static final String LOGINUSER = "login_user";
    public static final String USERNAME = "username";

}
