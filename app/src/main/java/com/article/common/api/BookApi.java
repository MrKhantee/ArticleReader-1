package com.article.common.api;

import com.article.common.Constant;
import com.article.core.book.bean.BookDetail;
import com.article.core.book.bean.BookListDetail;
import com.article.core.book.bean.BookListTags;
import com.article.core.book.bean.BookLists;
import com.article.core.book.bean.CategoryList;
import com.article.core.book.bean.RecommendBookList;
import com.article.core.book.bean.SubRankList;
import com.article.core.book.bean.TopRankList;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */

public class BookApi {
    public static BookApi instance;
    private BookApiService mApiService;

    public BookApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BOOK_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mApiService = retrofit.create(BookApiService.class);
    }

    /**
     * 获取BookApi实例
     *
     * @param client
     * @return
     */
    public static BookApi getInstance(OkHttpClient client) {
        if (instance == null) {
            instance = new BookApi(client);
        }
        return instance;
    }

    /**
     * 获取所有的排行榜
     *
     * @return
     */
    public Flowable<TopRankList> getTopRankList() {
        return mApiService.getTopRankList();
    }

    /**
     * 获取单一的排行榜
     *
     * @param rankingId
     * @return
     */
    public Flowable<SubRankList> getSubRanList(String rankingId) {
        return mApiService.getSubRankList(rankingId);
    }

    /**
     * 获取小说详情
     *
     * @param bookId
     * @return
     */
    public Flowable<BookDetail> getBookDetail(String bookId) {
        return mApiService.getBookDetail(bookId);
    }

    /**
     * 获取推荐的书单
     *
     * @param bookId
     * @return
     */
    public Flowable<RecommendBookList> getRecommendBookList(String bookId, String limit) {
        return mApiService.getRecommendBookList(bookId, limit);
    }

    /**
     * 获取书单详情
     *
     * @param bookListId
     * @return
     */
    public Flowable<BookListDetail> getBookListDetail(String bookListId) {
        return mApiService.getBookListDetail(bookListId);
    }

    /**
     * 获取书单列表
     *
     * @param duration
     * @param sort
     * @param start
     * @param limit
     * @param tag
     * @param gender
     * @return
     */
    public Flowable<BookLists> getBookLists(String duration, String sort, String start,
                                            String limit, String tag, String gender) {
        return mApiService.getBookLists(duration, sort, start, limit, tag, gender);
    }

    /**
     * 获取书单标签列表
     *
     * @return
     */
    public Flowable<BookListTags> getBookListTags() {
        return mApiService.getBookListTags();
    }

    /**
     * 小说分类
     *
     * @return
     */
    public Flowable<CategoryList> getCategoryList() {
        return mApiService.getTopCategoryList();
    }
}
