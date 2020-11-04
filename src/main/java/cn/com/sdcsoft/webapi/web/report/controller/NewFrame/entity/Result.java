package cn.com.sdcsoft.webapi.web.report.controller.NewFrame.entity;

import java.io.Serializable;

/**
 * 接口反馈数据结构
 *
 * @param <T>
 */
public class Result<T> implements Serializable {
    private Result() {
    }

    private static <E> Result<E> getResult(int status, String msg) {
        Result<E> result = new Result<E>();
        result.setStatus(status);
        result.setMsg(msg);
        return result;
    }

    private static <E> Result<E> getResult(String msg, E data) {
        Result<E> result = getResult(RESULT_CODE_SUCCESS, msg);
        result.setData(data);
        return result;
    }

    public static <E> Result<E> getSuccessResult() {
        return getResult(RESULT_CODE_SUCCESS, "success");
    }

    public static <E> Result<E> getSuccessResult(String msg) {
        return getResult(RESULT_CODE_SUCCESS, msg);
    }

    public static <E> Result<E> getSuccessResult(E data) {
        return getResult("success", data);
    }

    public static <E> Result<E> getSuccessResult(String msg, E data) {
        return getResult(msg, data);
    }

    public static <E> Result<E> getFailResult(String msg) {
        return getResult(RESULT_CODE_FAIL, msg);
    }

    public static <E> Result<E> getFailResult(int code, String msg) {
        return getResult(code, msg);
    }

    /**
     * 接口调用状态 成功
     */
    public static final int RESULT_CODE_SUCCESS = 0;
    /**
     * 接口调用状态 失败
     */
    public static final int RESULT_CODE_FAIL = 1;
    private String msg;
    private int status = RESULT_CODE_FAIL;
    private T data;

    public String getMsg() {

        return msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static int getResultCodeSuccess() {
        return RESULT_CODE_SUCCESS;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d,\"msg\":\"%s\"}",this.status,this.msg);
    }
}
