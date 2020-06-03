package com.wool.finance.controller;

public class Resp {

    private boolean success;

    private String error;

    private Object data;

    public Resp() {

    }
    public static Resp fail(Exception e) {
        return fail(e.getMessage());
    }

    public static Resp fail(String message) {
        Resp resp = new Resp();
        resp.success = false;
        resp.error = message == null ? "未知错误" : message;
        return resp;
    }

    public static Resp success() {
        return success(null);
    }

    public static Resp success(Object data) {
        Resp resp = new Resp();
        resp.success = true;
        resp.data = data;
        return resp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String message) {
        this.error = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
