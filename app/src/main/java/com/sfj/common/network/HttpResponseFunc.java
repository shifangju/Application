package com.sfj.common.network;

import com.sfj.android.contacts.NetWorkContacts;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/5/7.
 */
public class HttpResponseFunc<T> implements Func1<HttpResponse<T>,T> {

    @Override
    public T call(HttpResponse<T> httpResponse) {
        if(httpResponse.getStatus()!= NetWorkContacts.SUCCESS_CODE){
            throw new ApiException(httpResponse.getMsg(),httpResponse.getStatus());
        }

        return httpResponse.getData() ;
    }
}
