package com.sfj.common.network;

/**
 * Created by Administrator on 2016/5/7.
 */
public class ApiException extends RuntimeException {
    private int code;

    public ApiException(int code) {
        this.code = code;
    }

    public ApiException(String detailMessage, int code) {
        super(detailMessage);
        this.code = code;
    }

    public ApiException(String detailMessage, Throwable throwable, int code) {
        super(detailMessage, throwable);
        this.code = code;
    }

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
