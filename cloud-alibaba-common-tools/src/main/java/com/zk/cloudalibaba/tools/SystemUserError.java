package com.zk.cloudalibaba.tools;

public enum SystemUserError implements IErrorCode {
    USER_NAME_EXISTS("10100", "用户名已存在"),
    USER_NAME_NOT_EXISTS("10101", "用户名不存在"),
    PASSWORD_INCORRECT("10102", "密码错误"),
    LOGIN_FAILED("10103", "登录失败，用户名或密码错误"),
    OLD_PASSWORD_ERROR("10104", "旧密码错误"),;

    private String code;
    private String msg;

    private SystemUserError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}

