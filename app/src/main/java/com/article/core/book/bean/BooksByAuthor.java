package com.article.core.book.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/20.
 * Desc：
 */

public class BooksByAuthor extends Base {

    public List<BooksBean> books;


    public static class BooksBean {

        public String _id;
        public String title;
        public String author;
        public String shortIntro;
        public String cover;
        public String site;
        public String cat;
        public String majorCate;
        public String minorCate;
        public int latelyFollower;
        public String retentionRatio;
        public String lastChapter;
        public List<String> tags;


    }
}
