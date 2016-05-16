package com.sfj.android.data.model;

import com.sfj.android.data.impl.IUserModel;

/**
 * Created by Administrator on 2016/5/5.
 */
public class UserModel implements IUserModel{

    @Override
    public boolean login(String user, String password) {
        return user.equals(password);
    }
}
