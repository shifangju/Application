package com.sfj.android.data.bean;

/**
 * Created by Administrator on 2016/5/5.
 */
public class UserBean {
    String user_id;
    String user_truename;
    String user_name;
    String user_role_id;
    String user_role_name;
    String user_role_type;
    boolean aijuhe_founder;
    String phone;
    SellerInfo sellerinfo;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_truename() {
        return user_truename;
    }

    public void setUser_truename(String user_truename) {
        this.user_truename = user_truename;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(String user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getUser_role_name() {
        return user_role_name;
    }

    public void setUser_role_name(String user_role_name) {
        this.user_role_name = user_role_name;
    }

    public String getUser_role_type() {
        return user_role_type;
    }

    public void setUser_role_type(String user_role_type) {
        this.user_role_type = user_role_type;
    }

    public boolean isAijuhe_founder() {
        return aijuhe_founder;
    }

    public void setAijuhe_founder(boolean aijuhe_founder) {
        this.aijuhe_founder = aijuhe_founder;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SellerInfo getSellerinfo() {
        return sellerinfo;
    }

    public void setSellerinfo(SellerInfo sellerinfo) {
        this.sellerinfo = sellerinfo;
    }
}
