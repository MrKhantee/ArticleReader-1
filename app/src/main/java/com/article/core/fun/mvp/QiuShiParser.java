package com.article.core.fun.mvp;

import com.article.core.fun.bean.FunBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QiuShiParser {

	public List<FunBean> parserQiushiBaiKe(String strUrl) throws Exception {
		List<FunBean> funBeans = new ArrayList<>();
		Document document = Jsoup.connect(strUrl).get();
		Elements contentLeft = document.select("div#content-left");
		Elements items = contentLeft.select("div.article.block.untagged.mb15");
		for (Element element : items) {
			FunBean funBean = new FunBean();
			// id
			funBean.id = element.attr("id");
			Elements itemAuthors = element.select("div.author.clearfix > a");
			if (itemAuthors.size() != 0) {
				funBean.avator = itemAuthors.get(0).select("img").attr("src");
				funBean.author = itemAuthors.get(1).select("h2").text();
			} else {
				Elements noIcons = element.select("div.author.clearfix > span");
				funBean.avator = "https://qiushibaike.com" + noIcons.select("img").get(0).attr("src");
				funBean.author = noIcons.select("h2").text();
			}
			funBean.content = element.select("a.contentHerf > div.content > span").text();
			Elements thumb = element.select("div.thumb");
			if (thumb.size() != 0) {
				funBean.thumb = thumb.select("a > img").attr("src");
			}
			Elements stats = element.select("div.stats > span");
			funBean.statsVote = stats.get(0).select("i").text();
			funBean.statsComments = stats.get(1).select("a.qiushi_comments > i").text();
			funBeans.add(funBean);
		}

		return funBeans;
	}



	public static String stream2string(InputStream inputStream) {
		String encode = "utf-8";
		String str = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode));
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str).append("\n");
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
