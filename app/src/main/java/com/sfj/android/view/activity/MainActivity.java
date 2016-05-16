package com.sfj.android.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sfj.R;
import com.sfj.android.presenter.MainPresenter;
import com.sfj.android.presenter.view.IUserView;
import com.sfj.android.requests.MainRequest;
import com.sfj.android.requests.UserRequest;
import com.sfj.android.view.component.DaggerMainComponent;
import com.sfj.android.view.component.MainComponent;
import com.sfj.android.view.module.MainModule;
import com.sfj.common.activity.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IUserView ,View.OnClickListener{

    @Inject
    MainPresenter mainPresenter;

    MainComponent component;


    @Inject
    MainRequest service;

    @Inject
    UserRequest userRequest;

    EditText usernameEt;
    EditText passwordEt;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        component = DaggerMainComponent.builder().mainModule(new MainModule(this)).build();
        component.inject(this);
        findViewById(R.id.button).setOnClickListener(this);
        usernameEt = (EditText) findViewById(R.id.username_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
        tv = (TextView) findViewById(R.id.textview);
    }

    @Override
    public String getUser() {
        return usernameEt.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEt.getText().toString();
    }

    @Override
    public void loginSuccess() {
        showToast("Login success");
    }

    @Override
    public void showMsg(String msg) {
        this.usernameEt.setText(msg);
    }

    @Override
    public void showUserName(String username) {
        tv.setText(username);
    }


    @Override
    public void onClick(View v) {
        mainPresenter.userLogin(userRequest);
    }
}
