package com.sfj.android.requests;

import com.sfj.android.data.bean.UserBean;
import com.sfj.common.network.HttpResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/10.
 */
public interface UserRequest {
    @GET("api.php")
    Observable<HttpResponse<UserBean>> userLogin(@Query("mod")String mod, @Query("action")String action, @Query("username")String username, @Query("password")String password);

}
