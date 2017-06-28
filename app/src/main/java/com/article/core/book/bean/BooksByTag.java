package com.article.core.book.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/20.
 * Desc：根据标签查找小说
 */

public class BooksByTag extends Base {

    public List<TagBook> books;

    public static class TagBook {
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
