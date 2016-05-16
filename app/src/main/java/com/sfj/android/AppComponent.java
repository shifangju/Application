package com.sfj.android;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/5/9.
 */
@Singleton
@Component
public interface AppComponent {
    void inject(AppApplication appApplication);

}
