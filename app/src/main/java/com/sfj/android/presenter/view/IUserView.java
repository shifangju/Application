package com.sfj.android.presenter.view;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface IUserView {
    String getUser();
    String getPassword();
    void loginSuccess();

    void showMsg(String msg);
    void showUserName(String username);
}
