package com.sfj.common.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/5/3.
 */
public class BaseActivity extends AppCompatActivity {

    //供handler使用来确定是否显示loading dialog 不要设置为private 包共享即可 减少get set性能提升
    private boolean dataGot = false;
    protected boolean autoHideDialog = true;
    protected boolean showImmdediDialog = false;
    private Context mContext;
    public Handler httpHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showDialog() {

    }

    public void hideDialog() {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
