package com.sfj.android.requests;

import com.sfj.common.network.HttpResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/4.
 */
public interface MainRequest {

    @GET("api.php")
    Observable<HttpResponse<List<String>>> getStateList(@Query("key")String key);
}
