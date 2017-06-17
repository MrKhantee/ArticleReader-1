package com.article.core.book.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amos on 2017/6/10.
 * Desc：
 */

public class SubRankList extends Base {


    public RankingBean ranking;

    public static class RankingBean {
        public String _id;
        public String updated;
        public String title;
        public String tag;
        public String cover;
        public int __v;
        public String monthRank;
        public String totalRank;
        public boolean isSub;
        public boolean collapse;
        @SerializedName("new")
        public boolean newX;
        public String gender;
        public int priority;
        public String created;
        public String id;


        public List<BooksBean> books;

        public static class BooksBean {
            public String _id;
            public String title;
            public String author;
            public String shortIntro;
            public String cover;
            public String site;
            public String cat;
            public int banned;
            public int latelyFollower;
            public int latelyFollowerBase;
            public String minRetentionRatio;
            public String retentionRatio;
        }
    }
}
