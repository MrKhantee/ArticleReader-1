package com.article.common.api;

import com.article.core.welfare.bean.GankMeiziBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Amos on 2017/7/17.
 * Desc：
 */

public interface WelfareApiService {

    /**
     * 获取gankio里面的数据
     * @param number
     * @param page
     * @return
     */
    @GET("data/福利/{number}/{page}")
    Flowable<GankMeiziBean> getGankMeizi(@Path("number") int number, @Path("page") int page);
}
