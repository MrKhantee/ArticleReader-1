package com.article.core.book.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amos on 2017/6/14.
 * Desc：保存到数据库里面的数据
 */

public class CollectionBook extends RealmObject {
    //小说ID
    @PrimaryKey
    public String _id;
    //小说作者
    public String author;
    //小说封面
    public String cover;
    //小说简介
    public String shortIntro;
    //小说标题
    public String title;

    public boolean hasCp=true;
    //是否置顶
    public boolean isTop = false;
    //是否选中
    public boolean isSelected = false;
    //是否显示选中框
    public boolean showCheckBox = false;
    //是否是本地
    public boolean isFromSD = false;
    //路径
    public String path = "";
    //留存数
    public int latelyFollower;
    //留存率
    public String retentionRatio;
    //最后更新时间
    public String updated = "";
    //章节总数
    public int chaptersCount;
    //最后一章
    public String lastChapter;
    //最近阅读时间
    public String recentReadingTime = "";

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasCp() {
        return hasCp;
    }

    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean seleted) {
        isSelected = seleted;
    }

    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }

    public boolean isFromSD() {
        return isFromSD;
    }

    public void setFromSD(boolean fromSD) {
        isFromSD = fromSD;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public String getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(String retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public String getRecentReadingTime() {
        return recentReadingTime;
    }

    public void setRecentReadingTime(String recentReadingTime) {
        this.recentReadingTime = recentReadingTime;
    }

    @Override
    public String toString() {
        return "CollectionBook{" +
                "_id='" + _id + '\'' +
                ", author='" + author + '\'' +
                ", cover='" + cover + '\'' +
                ", shortIntro='" + shortIntro + '\'' +
                ", title='" + title + '\'' +
                ", hasCp=" + hasCp +
                ", isTop=" + isTop +
                ", isSelected=" + isSelected +
                ", showCheckBox=" + showCheckBox +
                ", isFromSD=" + isFromSD +
                ", path='" + path + '\'' +
                ", latelyFollower=" + latelyFollower +
                ", retentionRatio=" + retentionRatio +
                ", updated='" + updated + '\'' +
                ", chaptersCount=" + chaptersCount +
                ", lastChapter='" + lastChapter + '\'' +
                ", recentReadingTime='" + recentReadingTime + '\'' +
                '}';
    }
}
