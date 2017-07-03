package com.article.di.module;

import com.article.common.api.FunApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */
@Module
public class FunApiModule {
    @Provides
    protected FunApi providesFunApi(OkHttpClient okHttpClient){
        return FunApi.getInstance(okHttpClient);
    }
}
