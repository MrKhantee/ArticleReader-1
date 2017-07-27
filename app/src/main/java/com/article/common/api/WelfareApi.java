package com.article.common.api;

import com.article.CoreApplication;
import com.article.core.welfare.bean.GankMeiziBean;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amos on 2017/7/17.
 * Desc：
 */

public class WelfareApi {

    public static final String BASE_GANK_URL = "http://gank.io/api/";
    public static final String BASE_HUABAN_URL = "http://route.showapi.com/";
    public static final String BASE_DOUBAN_URL = "http://www.dbmeinv.com/dbgroup/";
    public static final String BASE_JIANDAN_URL = "http://jandan.net/";
    public static final String BASE_MEIZITU_URL = "http://www.mzitu.com/";

    private static OkHttpClient mOkHttpClient;
    private static WelfareApi instance;

    static {
        initOkHttpClient();
    }

    /**
     * 初始化OKHttpClient
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (WelfareApi.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(CoreApplication.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    public static WelfareApi getInstance() {
        if (instance == null) {
            instance = new WelfareApi();
        }
        return instance;
    }

    /**
     * Gank妹子Api
     *
     * @return
     */
    public static WelfareApiService getGankMeiziApi() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_GANK_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WelfareApiService gankMeiziService = mRetrofit.create(WelfareApiService.class);
        return gankMeiziService;
    }

    /**
     * 获取干货妹子
     *
     * @param number
     * @param page
     * @return
     */
    public Flowable<GankMeiziBean> getGankMeiZi(int number, int page) {
        return WelfareApi.getGankMeiziApi().getGankMeizi(number, page);
    }
}
