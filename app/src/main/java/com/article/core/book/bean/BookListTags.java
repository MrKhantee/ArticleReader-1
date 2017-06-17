package com.article.core.book.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public class BookListTags extends Base {


    public List<DataBean> data;

    public static class DataBean {
        public String name;
        public List<String> tags;
    }
}
