package com.article.core.fun.bean;

public class FunBean {
	public String id;
	public String author;
	public String avator;
	public String content;
	public String thumb;
	public String statsVote;
	public String statsComments;

	@Override
	public String toString() {
		return "HotBean [id=" + id + ", author=" + author + ", avator=" + avator + ", content=" + content + ", thumb="
				+ thumb + ", statsVote=" + statsVote + ", statsComments=" + statsComments + "]\n";
	}
}
