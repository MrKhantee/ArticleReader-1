package com.article.common.api;

import com.article.common.Constant;
import com.article.core.book.bean.AutoComplete;
import com.article.core.book.bean.BookDetail;
import com.article.core.book.bean.BookListDetail;
import com.article.core.book.bean.BookListTags;
import com.article.core.book.bean.BookLists;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.BookResource;
import com.article.core.book.bean.BooksByAuthor;
import com.article.core.book.bean.BooksByCats;
import com.article.core.book.bean.BooksByTag;
import com.article.core.book.bean.CategoryList;
import com.article.core.book.bean.CategoryListLv2;
import com.article.core.book.bean.ChangeResource;
import com.article.core.book.bean.ChapterRead;
import com.article.core.book.bean.HotWords;
import com.article.core.book.bean.Recommend;
import com.article.core.book.bean.RecommendBookList;
import com.article.core.book.bean.SearchDetail;
import com.article.core.book.bean.SubRankList;
import com.article.core.book.bean.TopRankList;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amos on 2017/6/9.
 * Desc：请求数据的入口
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
     * 推荐书籍
     *
     * @param gender
     * @return
     */
    public Flowable<Recommend> getRecommend(String gender) {
        return mApiService.getRecomend(gender);
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

    /**
     * 获取小说的二级分类
     *
     * @return
     */
    public Flowable<CategoryListLv2> getCategoryListLv2() {
        return mApiService.getCategoryListLv2();
    }

    /**
     * 根据小说分类获取小说内容
     *
     * @param gender
     * @param type
     * @param major
     * @param minjor
     * @param limit
     * @return
     */
    public Flowable<BooksByCats> getBooksByCats(String gender, String type, String major,
                                                String minjor, int start, int limit) {
        return mApiService.getBooksByCats(gender, type, major, minjor, start, limit);
    }

    /**
     * 获取小说的源
     *
     * @param view
     * @param book
     * @return
     */
    public Flowable<BookMixAToc> getBookMixToc(String book, String view) {
        return mApiService.getBookMixToc(book, view);
    }

    /**
     * 小说阅读
     *
     * @param strUrl
     * @return
     */
    public synchronized Flowable<ChapterRead> getChapterRead(String strUrl) {
        return mApiService.getChapterRead(strUrl);
    }

    /**
     * 根据标签获取小说
     *
     * @param tag
     * @param start
     * @param limit
     * @return
     */
    public Flowable<BooksByTag> getBooksByTag(String tag, String start, String limit) {
        return mApiService.getBooksByTag(tag, start, limit);
    }

    /**
     * 根据作者查找小说
     *
     * @param author
     * @return
     */
    public Flowable<BooksByAuthor> getBooksByAuthor(String author) {
        return mApiService.getBooksByAuthor(author);
    }

    /**
     * 实现小说换源
     *
     * @param sourceId
     * @param view
     * @return
     */
    public Flowable<ChangeResource> changeResource(String sourceId, String view) {
        return mApiService.changeResource(sourceId, view);
    }

    /**
     * 获取小说的源
     *
     * @param view summary
     * @param book 小说的id
     * @return
     */
    public Flowable<BookResource> getBookResource(String view, String book) {
        return mApiService.getBookResource(view, book);
    }

    /**
     * 获取大家都在搜
     *
     * @return
     */
    public Flowable<HotWords> getHotWords() {
        return mApiService.getHotWords();
    }

    /**
     * 关键字自动补全
     *
     * @param query
     * @return
     */
    public Flowable<AutoComplete> autoComplete(String query) {
        return mApiService.autoComplete(query);
    }

    /**
     * 查询小说
     *
     * @param query
     * @return
     */
    public Flowable<SearchDetail> searchBooks(String query) {
        return mApiService.searchBooks(query);
    }
}
