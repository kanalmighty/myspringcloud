package com.zk.cloudalibaba.tools;

/**
 * 公共错误码<br/>
 * 码段：10000~10099
 *
 *
 */
public enum CommonError implements IErrorCode {
    SUCCESS("0", "success"),
    UNKNOWN_ERROR("10000", "未知错误"),
    SYSTEM_ERROR("10001", "系统处理异常"),
    REQUEST_ERROR("10002", "请求地址或参数错误"),
    DB_PROCESS_FAILED("10003", "数据库处理失败"),
    DATA_NOT_FOUND("10004", "数据不存在"),
    PARAM_REQUIRED("10005", "参数必填[%s]"),
    PARAM_ILLEGAL("10006", "参数格式错误[%s]"),
    FILE_PROCESS_ERROR("10007", "文件处理失败"),
    FILE_SAVE_FAILED("10008", "文件保存失败"),
    FILE_GET_FAILED("10009", "文件获取失败"),
    LOGIN_TIMEOUT("10010", "登录超时，请重新登录！"),
    NO_PERMISSION("10011", "无访问权限");

    private String code;
    private String msg;

    private CommonError(String code, String msg) {
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