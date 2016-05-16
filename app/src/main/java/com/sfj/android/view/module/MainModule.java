package com.sfj.android.view.module;

import com.sfj.android.presenter.MainPresenter;
import com.sfj.android.presenter.view.IUserView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/5/5.
 */
@Module
public class MainModule {

    private IUserView userView;

    public MainModule(IUserView userView) {
        this.userView = userView;
    }

    @Provides
    public MainPresenter provideUserPresenter(){
        return new MainPresenter(userView);
    }

}
