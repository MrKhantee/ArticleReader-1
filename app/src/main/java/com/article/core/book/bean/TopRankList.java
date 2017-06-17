package com.article.core.book.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */

public class TopRankList extends Base {


    public List<MaleBean> male;


    public List<MaleBean> female;

    public List<MaleBean> getMale() {
        return male;
    }

    public void setMale(List<MaleBean> male) {
        this.male = male;
    }

    public List<MaleBean> getFemale() {
        return female;
    }

    public void setFemale(List<MaleBean> female) {
        this.female = female;
    }


    public static class MaleBean {
        /**
         * _id : 54d42d92321052167dfb75e3
         * title : 追书最热榜 Top100
         * cover : /ranking-cover/142319144267827
         * collapse : false
         * monthRank : 564d820bc319238a644fb408
         * totalRank : 564d8494fe996c25652644d2
         * shortTitle : 最热榜
         */

        private String _id;
        public String title;
        public String cover;
        public boolean collapse;
        public String monthRank;
        public String totalRank;
        public String shortTitle;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isCollapse() {
            return collapse;
        }

        public void setCollapse(boolean collapse) {
            this.collapse = collapse;
        }

        public String getMonthRank() {
            return monthRank;
        }

        public void setMonthRank(String monthRank) {
            this.monthRank = monthRank;
        }

        public String getTotalRank() {
            return totalRank;
        }

        public void setTotalRank(String totalRank) {
            this.totalRank = totalRank;
        }

        public String getShortTitle() {
            return shortTitle;
        }

        public void setShortTitle(String shortTitle) {
            this.shortTitle = shortTitle;
        }

        @Override
        public String toString() {
            return "MaleBean{" +
                    "_id='" + _id + '\'' +
                    ", title='" + title + '\'' +
                    ", cover='" + cover + '\'' +
                    ", collapse=" + collapse +
                    ", monthRank='" + monthRank + '\'' +
                    ", totalRank='" + totalRank + '\'' +
                    ", shortTitle='" + shortTitle + '\'' +
                    '}';
        }
    }


}
