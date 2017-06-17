package com.article.core.book.bean;

import java.util.List;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public class BookListDetail extends Base {



    private BookListBean bookList;

    public BookListBean getBookList() {
        return bookList;
    }

    public void setBookList(BookListBean bookList) {
        this.bookList = bookList;
    }

    public static class BookListBean {


        private String _id;
        private String updated;
        private String title;
        private AuthorBean author;
        private String desc;
        private String gender;
        private String created;
        private Object stickStopTime;
        private boolean isDraft;
        private Object isDistillate;
        private int collectorCount;
        private String shareLink;
        private String id;
        private int total;
        private List<String> tags;
        private List<BooksBean> books;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public Object getStickStopTime() {
            return stickStopTime;
        }

        public void setStickStopTime(Object stickStopTime) {
            this.stickStopTime = stickStopTime;
        }

        public boolean isIsDraft() {
            return isDraft;
        }

        public void setIsDraft(boolean isDraft) {
            this.isDraft = isDraft;
        }

        public Object getIsDistillate() {
            return isDistillate;
        }

        public void setIsDistillate(Object isDistillate) {
            this.isDistillate = isDistillate;
        }

        public int getCollectorCount() {
            return collectorCount;
        }

        public void setCollectorCount(int collectorCount) {
            this.collectorCount = collectorCount;
        }

        public String getShareLink() {
            return shareLink;
        }

        public void setShareLink(String shareLink) {
            this.shareLink = shareLink;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<BooksBean> getBooks() {
            return books;
        }

        public void setBooks(List<BooksBean> books) {
            this.books = books;
        }

        public static class AuthorBean {


            private String _id;
            private String avatar;
            private String nickname;
            private String type;
            private int lv;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getLv() {
                return lv;
            }

            public void setLv(int lv) {
                this.lv = lv;
            }
        }

        public static class BooksBean {
            /**
             * book : {"_id":"5729a557af3ae4b032061554","title":"魔神乐园","author":"熊狼狗","longIntro":"\u201c世间第一的剑术天赋，消耗寿命72年，交易后剩余寿命5年。同时你也将永远感觉不到爱情，亲情和友情，一生都将孤独终老，断子绝孙。\r\n从此以后，人世间一切的幸福都将和你没有关系，你愿意么？\u201d\r\n\u201c哈哈哈哈，我孤苦无依，万念俱灰，身负不共戴天之仇，我怎么可能会不愿意？我怎么可能会不想要？我求之不得！！\u201d\r\n------------------------------------------\r\n剑扫神州七日夜，纵横星空九万里。\r\n上斩圣佛，下斩妖魔，荡尽心中不平事。\r\n-----------------------------------------\r\n唯一书友群：431114082\r\n唯一公众号：熊狼狗","cover":"/agent/http://image.cmfu.com/books/1003459786/1003459786.jpg","cat":"玄幻","site":"zhuishuvip","majorCate":"玄幻","minorCate":"异界大陆","banned":0,"latelyFollower":5636,"wordCount":2490702,"retentionRatio":56.88}
             * comment :
             */

            private BookBean book;
            private String comment;

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public static class BookBean {


                private String _id;
                private String title;
                private String author;
                private String longIntro;
                private String cover;
                private String cat;
                private String site;
                private String majorCate;
                private String minorCate;
                private int banned;
                private int latelyFollower;
                private int wordCount;
                private double retentionRatio;

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

                public String getSite() {
                    return site;
                }

                public void setSite(String site) {
                    this.site = site;
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

                public int getBanned() {
                    return banned;
                }

                public void setBanned(int banned) {
                    this.banned = banned;
                }

                public int getLatelyFollower() {
                    return latelyFollower;
                }

                public void setLatelyFollower(int latelyFollower) {
                    this.latelyFollower = latelyFollower;
                }

                public int getWordCount() {
                    return wordCount;
                }

                public void setWordCount(int wordCount) {
                    this.wordCount = wordCount;
                }

                public double getRetentionRatio() {
                    return retentionRatio;
                }

                public void setRetentionRatio(double retentionRatio) {
                    this.retentionRatio = retentionRatio;
                }
            }
        }
    }
}
