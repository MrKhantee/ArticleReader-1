package com.article.core.book.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public class BookLists extends Base {


    public List<BookListsBean> bookLists;

    public class BookListsBean implements Serializable {
        public String _id;
        public String title;
        public String author;
        public String desc;
        public String gender;
        public int collectorCount;
        public String cover;
        public int bookCount;
    }
}
