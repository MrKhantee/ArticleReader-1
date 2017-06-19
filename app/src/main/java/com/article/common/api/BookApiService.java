package com.article.common.api;

import com.article.core.book.bean.BookDetail;
import com.article.core.book.bean.BookListDetail;
import com.article.core.book.bean.BookListTags;
import com.article.core.book.bean.BookLists;
import com.article.core.book.bean.BooksByCats;
import com.article.core.book.bean.CategoryList;
import com.article.core.book.bean.CategoryListLv2;
import com.article.core.book.bean.RecommendBookList;
import com.article.core.book.bean.SubRankList;
import com.article.core.book.bean.TopRankList;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */

public interface BookApiService {
    /**
     * 获取所有的排行榜
     * http://api.zhuishushenqi.com/ranking/gender
     *
     * @return
     */
    @GET("/ranking/gender")
    Flowable<TopRankList> getTopRankList();

    /**
     * 获取单一的排行榜
     * http://api.zhuishushenqi.com/ranking/54d42d92321052167dfb75e3
     *
     * @param rankingId
     * @return
     */
    @GET("/ranking/{rankingId}")
    Flowable<SubRankList> getSubRankList(@Path("rankingId") String rankingId);

    /**
     * 小说详情
     * http://api.zhuishushenqi.com/book/57206c3539a913ad65d35c7b
     *
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}")
    Flowable<BookDetail> getBookDetail(@Path("bookId") String bookId);

    /**
     * 获取小说的推荐书单
     * http://api.zhuishushenqi.com/book-list/57206c3539a913ad65d35c7b/recommend?limit=10
     *
     * @param bookId
     * @return
     */
    @GET("/book-list/{bookId}/recommend")
    Flowable<RecommendBookList> getRecommendBookList(@Path("bookId") String bookId
            , @Query("limit") String limit);

    /**
     * http://api.zhuishushenqi.com/book-list/556e5784c951a008329860f7
     * 获取书单详情
     *
     * @param bookListId 书单Id
     * @return
     */
    @GET("/book-list/{bookListId}")
    Flowable<BookListDetail> getBookListDetail(@Path("bookListId") String bookListId);

    /**
     * 获取主题书单列表
     * 本周最热：http://api.zhuishushenqi.com/book-list?duration=last-seven-days&sort=collectorCount
     * 最新发布：http://api.zhuishushenqi.com/book-list?duration=all&sort=created
     * 最多收藏：http://api.zhuishushenqi.com/book-list?duration=all&sort=collectorCount
     *
     * @param tag    都市、古代、架空、重生、玄幻、网游
     * @param gender male、female
     * @param limit  20
     * @return
     */
    @GET("/book-list")
    Flowable<BookLists> getBookLists(@Query("duration") String duration,
                                     @Query("sort") String sort,
                                     @Query("start") String start,
                                     @Query("limit") String limit,
                                     @Query("tag") String tag,
                                     @Query("gender") String gender);

    /**
     * 获取主题书单标签列表
     * http://api.zhuishushenqi.com/book-list/tagType
     *
     * @return
     */
    @GET("/book-list/tagType")
    Flowable<BookListTags> getBookListTags();

    /**
     * 获取小说顶级分类
     * http://api.zhuishushenqi.com/cats/lv2/statistics
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Flowable<CategoryList> getTopCategoryList();

    /**
     * 获取小说的二级分类
     * http://api.zhuishushenqi.com/cats/lv2
     *
     * @return
     */
    @GET("/cats/lv2")
    Flowable<CategoryListLv2> getCategoryListLv2();

    /**
     * 根据小说类型获取数据
     * http://api.zhuishushenqi.com/book/by-categories?gender=male&type=hot&major=玄幻&minjor=东方玄幻&limit=50
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  顶级分类：玄幻
     * @param minjor 二级分类：东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  请求数据条数，默认是：50
     * @return
     */
    @GET("/book/by-categories")
    Flowable<BooksByCats> getBooksByCats(@Query("gender") String gender,
                                         @Query("type") String type,
                                         @Query("major") String major,
                                         @Query("minjor") String minjor,
                                         @Query("start") int start,
                                         @Query("limit") int limit);

}
