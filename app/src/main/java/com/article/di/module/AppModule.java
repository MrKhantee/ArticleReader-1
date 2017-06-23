package com.article.di.module;

import android.content.Context;

import com.article.CoreApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */
@Module
public class AppModule {

    private final CoreApplication mCoreApplication;

    public AppModule(CoreApplication coreApplication) {
        this.mCoreApplication = coreApplication;
    }

    @Provides
    CoreApplication providesApplication() {
        return mCoreApplication;
    }

    @Provides
    Context providesContext() {
        return mCoreApplication;
    }
}
