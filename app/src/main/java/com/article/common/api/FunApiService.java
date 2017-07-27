package com.article.common.api;

import com.article.core.fun.FunBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */

public interface FunApiService {


    /**
     * https://m2.qiushibaike.com/article/list/suggest?page=1&count=30
     * 获取糗事百科的数据
     *
     * @param page
     * @param count
     * @return
     */
    @GET("/article/list/suggest")
    Flowable<FunBean> getQiuShiBaiKe(@Query("page") int page,
                                     @Query("count") int count);

}
