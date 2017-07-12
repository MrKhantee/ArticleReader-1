package com.article.common.api;

import com.article.common.Constant;
import com.squareup.okhttp.ResponseBody;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
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
     * 获取糗事百科
     *
     * @param tag
     * @param page
     * @return
     */
    public  Flowable<ResponseBody> getQiuShi(String tag, String page) {
        return mFunApiService.getQiushi(tag, page, "4996894");
    }
}
