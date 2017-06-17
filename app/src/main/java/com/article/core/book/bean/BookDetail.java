package com.article.core.book.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/12.
 * Desc：
 */

public class BookDetail extends Base {

    /**
     * _id : 57206c3539a913ad65d35c7b
     * title : 一念永恒
     * author : 耳根
     * longIntro : 一念成沧海，一念化桑田。一念斩千魔，一念诛万仙。唯我念……永恒
     * cover : /agent/http://image.cmfu.com/books/1003354631/1003354631.jpg
     * cat : 幻想修仙
     * majorCate : 仙侠
     * minorCate : 幻想修仙
     * creater : Xiaomi Redmi 3
     * currency : 0
     * contentType : txt
     * _le : false
     * allowMonthly : false
     * allowVoucher : true
     * allowBeanVoucher : false
     * hasCp : true
     * postCount : 47065
     * latelyFollower : 265551
     * followerCount : 0
     * wordCount : 2437985
     * serializeWordCount : 6382
     * retentionRatio : 75.64
     * updated : 2017-06-11T10:15:21.405Z
     * isSerial : true
     * chaptersCount : 839
     * lastChapter : 第839章 绝世之战！
     * gender : ["male"]
     * tags : ["仙侠"]
     * donate : false
     * copyright : 阅文集团正版授权
     */

    private String _id;
    private String title;
    private String author;
    private String longIntro;
    private String cover;
    private String cat;
    private String majorCate;
    private String minorCate;
    private String creater;
    private int currency;
    private String contentType;
    private boolean _le;
    private boolean allowMonthly;
    private boolean allowVoucher;
    private boolean allowBeanVoucher;
    private boolean hasCp;
    private int postCount;
    //追书人数
    private int latelyFollower;
    //读者留存率
    private int followerCount;
    //小说总字数
    private int wordCount;
    //每日更新字数
    private int serializeWordCount;
    private String retentionRatio;
    private String updated;
    private boolean isSerial;
    //小说章节数
    private int chaptersCount;
    //小说最新章节
    private String lastChapter;
    private boolean donate;
    private String copyright;
    private List<String> gender;
    private List<String> tags;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLongIntro() {
        return longIntro;
    }

    public void setLongIntro(String longIntro) {
        this.longIntro = longIntro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getMajorCate() {
        return majorCate;
    }

    public void setMajorCate(String majorCate) {
        this.majorCate = majorCate;
    }

    public String getMinorCate() {
        return minorCate;
    }

    public void setMinorCate(String minorCate) {
        this.minorCate = minorCate;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean is_le() {
        return _le;
    }

    public void set_le(boolean _le) {
        this._le = _le;
    }

    public boolean isAllowMonthly() {
        return allowMonthly;
    }

    public void setAllowMonthly(boolean allowMonthly) {
        this.allowMonthly = allowMonthly;
    }

    public boolean isAllowVoucher() {
        return allowVoucher;
    }

    public void setAllowVoucher(boolean allowVoucher) {
        this.allowVoucher = allowVoucher;
    }

    public boolean isAllowBeanVoucher() {
        return allowBeanVoucher;
    }

    public void setAllowBeanVoucher(boolean allowBeanVoucher) {
        this.allowBeanVoucher = allowBeanVoucher;
    }

    public boolean isHasCp() {
        return hasCp;
    }

    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getSerializeWordCount() {
        return serializeWordCount;
    }

    public void setSerializeWordCount(int serializeWordCount) {
        this.serializeWordCount = serializeWordCount;
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

    public boolean isIsSerial() {
        return isSerial;
    }

    public void setIsSerial(boolean isSerial) {
        this.isSerial = isSerial;
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

    public boolean isDonate() {
        return donate;
    }

    public void setDonate(boolean donate) {
        this.donate = donate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
