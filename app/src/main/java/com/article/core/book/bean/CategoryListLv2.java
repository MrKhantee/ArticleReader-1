package com.article.core.book.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/17.
 * Desc：小说的二级分类
 */

public class CategoryListLv2 extends Base {
    /**
     * major : 玄幻
     * mins : ["东方玄幻","异界大陆","异界争霸","远古神话"]
     */

    public List<MaleBean> male;
    /**
     * major : 古代言情
     * mins : ["穿越时空","古代历史","古典架空","宫闱宅斗","经商种田"]
     */

    public List<MaleBean> female;

    public static class MaleBean {
        public String major;
        public List<String> mins;
    }
}
