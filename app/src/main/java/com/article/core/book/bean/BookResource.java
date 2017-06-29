package com.article.core.book.bean;

/**
 * Created by Amos on 2017/6/28.
 * Desc：
 */

public class BookResource extends Base {


    /**
     * _id : 56f28f3ed1f822614cff4460
     * source : zhuishuvip
     * name : 优质书源
     * link : http://vip.zhuishushenqi.com/toc/56f28f3ed1f822614cff4460
     * lastChapter : 第1592章 奖励
     * isCharge : false
     * chaptersCount : 1554
     * updated : 2017-06-28T13:14:16.337Z
     * starting : true
     * host : vip.zhuishushenqi.com
     */

    public String _id;
    public String source;
    public String name;
    public String link;
    public String lastChapter;
    public boolean isCharge;
    public int chaptersCount;
    public String updated;
    public boolean starting;
    public String host;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public boolean isIsCharge() {
        return isCharge;
    }

    public void setIsCharge(boolean isCharge) {
        this.isCharge = isCharge;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
