package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.common.Constant;
import com.article.common.utils.LogUtils;
import com.article.common.utils.SharedPreferencesUtils;
import com.article.common.utils.StatusBarCompat;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.ChapterRead;
import com.article.core.book.bean.Recommend;
import com.article.core.book.contract.BookReadContract;
import com.article.core.book.manager.CacheManager;
import com.article.core.book.presenter.BookReadPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;
import com.article.widget.read.CustomReadView;
import com.article.widget.read.OnReadStateChangeListener;
import com.article.widget.read.OverlappedWidget;
import com.article.widget.read.PageWidget;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.article.R.id.rlReadMark;

/**
 * Created by Amos on 2017/6/19.
 * Desc：
 */

public class BookReadActivity extends BaseActivity implements BookReadContract.View {

    //全部页面
    @BindView(R.id.flReadWidget)
    FrameLayout mFlReadWidget;
    //返回键
    @BindView(R.id.ivBack)
    ImageView mIvBack;
    //小说标题
    @BindView(R.id.tvBookReadTocTitle)
    TextView mTvBookReadTocTitle;
    //朗读
    @BindView(R.id.tvBookReadReading)
    TextView mTvBookReadReading;
    //社区
    @BindView(R.id.tvBookReadCommunity)
    TextView mTvBookReadCommunity;
    //简介
    @BindView(R.id.tvBookReadIntroduce)
    TextView mTvBookReadIntroduce;
    //换源
    @BindView(R.id.tvBookReadSource)
    TextView mTvBookReadSource;

    //
    @BindView(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;

    //下载进度
    @BindView(R.id.tvDownloadProgress)
    TextView mTvDownloadProgress;
    //最小亮度
    @BindView(R.id.ivBrightnessMinus)
    ImageView mIvBrightnessMinus;
    //亮度进度条
    @BindView(R.id.seekbarLightness)
    SeekBar mSeekbarLightness;
    //最大亮度
    @BindView(R.id.ivBrightnessPlus)
    ImageView mIvBrightnessPlus;
    //最小字体
    @BindView(R.id.tvFontsizeMinus)
    TextView mTvFontsizeMinus;
    //字体进度条
    @BindView(R.id.seekbarFontSize)
    SeekBar mSeekbarFontSize;
    //最大字体
    @BindView(R.id.tvFontsizePlus)
    TextView mTvFontsizePlus;
    //音量键翻页
    @BindView(R.id.cbVolume)
    CheckBox mCbVolume;
    //自动调整亮度
    @BindView(R.id.cbAutoBrightness)
    CheckBox mCbAutoBrightness;
    //主题
    @BindView(R.id.gvTheme)
    GridView mGvTheme;


    //新增书签
    @BindView(R.id.tvAddMark)
    TextView mTvAddMark;
    //清除书签
    @BindView(R.id.tvClear)
    TextView mTvClear;
    //书签列表
    @BindView(R.id.lvMark)
    ListView mLvMark;

    @BindView(R.id.rlReadAaSet)
    LinearLayout rlReadAaSet;


    @BindView(rlReadMark)
    LinearLayout mRlReadMark;
    //阅读模式，夜间或者日间，默认是日间
    @BindView(R.id.tvBookReadMode)
    TextView mTvBookReadMode;
    //阅读设置，点击显示设置面板
    @BindView(R.id.tvBookReadSettings)
    TextView mTvBookReadSettings;
    //缓存，点击显示缓存面板
    @BindView(R.id.tvBookReadDownload)
    TextView mTvBookReadDownload;
    //书签，点击显示书签
    @BindView(R.id.tvBookMark)
    TextView mTvBookMark;
    //目录，点击显示目录
    @BindView(R.id.tvBookReadToc)
    TextView mTvBookReadToc;

    @BindView(R.id.llBookReadBottom)
    LinearLayout mLlBookReadBottom;
    @BindView(R.id.rlBookReadRoot)
    RelativeLayout mRlBookReadRoot;

    @Inject
    BookReadPresenter mPresenter;

    public static final String INTENT_BEAN = "recommendBooksBean";
    public static final String INTENT_SD = "isFromSD";

    protected int statusBarColor = 0;

    private Recommend.RecommendBooks mBooks;
    private String bookId;
    private boolean isFromSd;

    private CustomReadView mPageWidget;
    /**
     * 是否开始阅读章节
     **/
    private boolean startRead = false;
    //当前页面的章节
    private int currentChapter = 1;
    private View decodeView;
    private int curTheme = -1;
    private List<BookMixAToc.mixToc.Chapters> mChapterList = new ArrayList<>();

    protected View statusBarView = null;

    public static void startActivity(Context context, Recommend.RecommendBooks recommendBooks) {
        startActivity(context, recommendBooks, false);
    }

    public static void startActivity(Context context, Recommend.RecommendBooks recommendBooks, boolean isFromSd) {
        context.startActivity(new Intent(context, BookReadActivity.class)
                .putExtra(INTENT_BEAN, recommendBooks).putExtra(INTENT_SD, isFromSd));
    }

    @Override
    protected int getLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        statusBarColor = ContextCompat.getColor(this, R.color.reader_menu_bg_color);
        return R.layout.activity_read;
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);

        mBooks = (Recommend.RecommendBooks) getIntent().getSerializableExtra(INTENT_BEAN);
        bookId = mBooks._id;
        isFromSd = getIntent().getBooleanExtra(INTENT_SD, false);


//        EventBus.getDefault().register(this);

        mTvBookReadTocTitle.setText(mBooks.title);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void configViews() {
        decodeView = getWindow().getDecorView();

        if (statusBarColor == 0) {
            statusBarView = StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        } else if (statusBarColor != -1) {
            statusBarView = StatusBarCompat.compat(this, statusBarColor);
        }

        initPagerWidget();


    }

    /**
     * 初始化阅读页面
     */
    private void initPagerWidget() {
        if (SharedPreferencesUtils.getInstance().getInt(Constant.FLIP_STYLE, 0) == 0) {
            mPageWidget = new PageWidget(this, bookId, mChapterList, new ReadListener());
        } else {
            mPageWidget = new OverlappedWidget(this, bookId, mChapterList, new ReadListener());
        }
    }


    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showBookToc(List<BookMixAToc.mixToc.Chapters> list) {

    }

    @Override
    public void showChapterRead(ChapterRead.Chapter data, int chapter) {
        if (data != null) {
            CacheManager.getInstance().saveChapterFile(bookId, chapter, data);
        }
        if (!startRead) {
            startRead = true;
            currentChapter = chapter;
            if (!mPageWidget.isPrepared) {
                mPageWidget.init(curTheme);
            } else {
                mPageWidget.jumpToChapter(currentChapter);
            }
        }
    }

    @Override
    public void netError(int chapter) {

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    private synchronized void toggleReadBar() { // 切换工具栏 隐藏/显示 状态
        if (isVisible(mLlBookReadTop)) {
            hideReadBar();
        } else {
            showReadBar();
        }
    }

    /**
     * 隐藏阅读界面的工具栏
     */
    private synchronized void hideReadBar() {
        gone(mTvDownloadProgress, mLlBookReadBottom, mLlBookReadTop, rlReadAaSet, mRlReadMark);
        hideStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    /**
     * 显示工具栏
     */

    private synchronized void showReadBar() {
        visible(mLlBookReadBottom, mLlBookReadTop);
        showStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }


    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if (statusBarView != null) {
            statusBarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if (statusBarView != null) {
            statusBarView.setBackgroundColor(statusBarColor);
        }
    }

    private class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
            LogUtils.i("onChapterChanged:" + chapter);
            currentChapter = chapter;
//            mTocListAdapter.setCurrentChapter(currentChapter);
            // 加载前一节 与 后三节
            for (int i = chapter - 1; i <= chapter + 3 && i <= mChapterList.size(); i++) {
                if (i > 0 && i != chapter
                        && CacheManager.getInstance().getChapterFile(bookId, i) == null) {
                    mPresenter.getChapterRead(mChapterList.get(i - 1).link, i);
                }
            }
        }

        @Override
        public void onPageChanged(int chapter, int page) {

        }

        @Override
        public void onLoadChapterFailure(int chapter) {
            startRead = false;
            if (CacheManager.getInstance().getChapterFile(bookId, chapter) == null)
                mPresenter.getChapterRead(mChapterList.get(chapter - 1).link, chapter);
        }

        @Override
        public void onCenterClick() {
            toggleReadBar();
        }

        @Override
        public void onFlip() {
            hideReadBar();
        }
    }
}
