package com.sfj.android;

import android.app.Application;

import com.sfj.common.image.ImageLoaderConfig;

/**
 * Created by Administrator on 2016/5/3.
 */
public class AppApplication extends Application {


//    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化图片加载、缓存框架
        ImageLoaderConfig.initImageLoader(this);

//        this.component = Dagger_ApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
//        this.component.injectApplication(this);
    }

//    ApplicationComponent getComponent(){
//        return this.component;
//    }

}
