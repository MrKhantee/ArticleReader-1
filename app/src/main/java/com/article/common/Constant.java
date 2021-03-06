package com.article.common;

import android.graphics.Color;
import android.support.annotation.StringDef;

import com.article.common.utils.AppUtils;
import com.article.common.utils.FileUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */

public class Constant {
    /**
     * 小说的基类API接口地址
     */
    public static final String BOOK_BASE_URL = "http://api.zhuishushenqi.com";
    /**
     * 小说阅读需要的base url
     */
    public static final String BOOK_READER_BASE_URL = "http://chapter2.zhuishushenqi.com";
    /**
     * 小说图片的base url
     */
    public static final String BOOK_IMAGE_BASE_URL = "http://statics.zhuishushenqi.com";

    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";

    public static String PATH_COLLECT = FileUtils.createRootPath(AppUtils.getAppContext()) + "/collect";

    public static String PATH_TXT = PATH_DATA + "/book/";

    public static String PATH_EPUB = PATH_DATA + "/epub";

    public static String PATH_CHM = PATH_DATA + "/chm";
    public static final String IS_NIGHT = "isNight";
    public static String BASE_PATH = AppUtils.getAppContext().getCacheDir().getPath();

    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";
    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    @StringDef({
            Gender.MALE,
            Gender.FEMALE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        String MALE = "male";

        String FEMALE = "female";
    }

    @StringDef({
            CateType.HOT,
            CateType.NEW,
            CateType.REPUTATION,
            CateType.OVER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CateType {
        String HOT = "hot";

        String NEW = "new";

        String REPUTATION = "reputation";

        String OVER = "over";
    }

    public static final String ISBYUPDATESORT = "isByUpdateSort";
    public static final String FLIP_STYLE = "flipStyle";

    //糗事百科的基类url
    public static final String FUN_BASE_URL = "https://m2.qiushibaike.com";
}
