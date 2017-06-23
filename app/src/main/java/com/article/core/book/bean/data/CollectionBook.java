package com.article.core.book.bean.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public class CollectionBook extends RealmObject {
    @PrimaryKey
    private String _id;
    private String author;
    private String cover;
    private String title;
    private String updated;
    private String lastChapter;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    @Override
    public String toString() {
        return "CollectionBook{" +
                "_id='" + _id + '\'' +
                ", author='" + author + '\'' +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", updated='" + updated + '\'' +
                ", lastChapter='" + lastChapter + '\'' +
                '}';
    }
}
