package com.article.common.api;

import com.squareup.okhttp.ResponseBody;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */

public interface FunApiService {
    //热门
    //https://www.qiushibaike.com/8hr/page/2/?s=4996894
    //24小时
    //https://www.qiushibaike.com/hot/page/2/?s=4996894
    //热图
    //https://www.qiushibaike.com/imgrank/page/2/?s=4996894
    //文字
    //https://www.qiushibaike.com/text/page/2/?s=4996894
    //糗图
    //https://www.qiushibaike.com/pic/page/2/?s=4996894
    //新鲜
    //https://www.qiushibaike.com/textnew/page/2/?s=4996894

    /**
     * 拉取糗事百科的内容
     *
     * @param tag  分类
     * @param page 页面
     * @param s    默认是4996894
     * @return
     */
    @GET("/{tag}/page/{page}/")
    Flowable<ResponseBody> getQiushi(@Path("tag") String tag,
                                     @Path("page") String page,
                                     @Query("s") String s);

}
