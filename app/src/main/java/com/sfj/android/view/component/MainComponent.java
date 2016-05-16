package com.sfj.android.view.component;

import com.sfj.android.view.activity.MainActivity;
import com.sfj.android.view.module.ApiServiceModule;
import com.sfj.android.view.module.MainModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/5/5.
 */
@Singleton
@Component(modules = {MainModule.class, ApiServiceModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
