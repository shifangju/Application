package com.sfj.common.network;

import com.sfj.common.log.MLog;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/5/14.
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        MLog.e("TAG","onError--->"+e.getMessage());
        /**
         * 返回的status不为 1 则为ApiException;
         */
        if(e instanceof ApiException) {

        }
    }

}
