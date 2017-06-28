package com.article.core.fun.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/27.
 * Desc：
 */

public class HotList {

    public List<HotBean> mBeanList;

    public class HotBean {
        public String author;
        public String athorConver;
        public String content;
        //好笑
        public String vote;
        //评论
        public String comments;
    }
}
