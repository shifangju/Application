package com.sfj.android.presenter;

import com.sfj.android.data.bean.UserBean;
import com.sfj.android.data.impl.IUserModel;
import com.sfj.android.data.model.UserModel;
import com.sfj.android.presenter.view.IUserView;
import com.sfj.android.requests.UserRequest;
import com.sfj.common.network.HttpResponseFunc;
import com.sfj.common.network.HttpSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/5.
 */
public class MainPresenter {
    private IUserModel userModel;
    private IUserView userView;

    public MainPresenter(IUserView userView) {
        this.userModel = new UserModel();
        this.userView = userView;
    }

    public void userLogin(UserRequest userRequest) {
        userRequest.userLogin("sellerapp","login",userView.getUser(),userView.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResponseFunc<UserBean>())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new HttpSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        userView.showUserName(userBean.getUser_truename());
                    }
                });

    }
}
