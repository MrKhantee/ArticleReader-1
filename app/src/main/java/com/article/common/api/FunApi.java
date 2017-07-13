package com.article.common.api;

import com.article.common.Constant;
import com.article.core.fun.FunBean;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */

public class FunApi {

    public static FunApi instance;
    private  FunApiService mFunApiService;

    public FunApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.FUN_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mFunApiService = retrofit.create(FunApiService.class);
    }

    /**
     * 获取BookApi实例
     *
     * @param client
     * @return
     */
    public static FunApi getInstance(OkHttpClient client) {
        if (instance == null) {
            instance = new FunApi(client);
        }
        return instance;
    }



    /**
     * 获取糗事百科数据
     * @param page
     * @param count
     * @return
     */
    public Flowable<FunBean> getQiuShiBaiKe(String page, String count){
        return mFunApiService.getQiuShiBaiKe(page, count);
    }
}
