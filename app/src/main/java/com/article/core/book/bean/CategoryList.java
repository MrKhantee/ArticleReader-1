package com.article.core.book.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/17.
 * Desc：
 */

public class CategoryList extends Base {

    public List<MaleBean> male;
    public List<MaleBean> female;

    public static class MaleBean {
        public String name;
        public int bookCount;
    }
}
