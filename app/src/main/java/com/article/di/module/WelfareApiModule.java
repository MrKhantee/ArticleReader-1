package com.article.di.module;

import com.article.common.api.WelfareApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Amos on 2017/7/26.
 * Desc：
 */

@Module
public class WelfareApiModule {

    @Provides
    protected WelfareApi providesWelfareApi() {
        return WelfareApi.getInstance();
    }

}
