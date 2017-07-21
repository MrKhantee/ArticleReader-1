package com.article.core.welfare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amos on 2017/7/17.
 * Desc：
 */

public class GankMeiziResult {
    public boolean error;

    @SerializedName("gankmeiziresults")
    public List<GankMeiziInfo> gankMeizis;
}
