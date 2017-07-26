package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.common.Constant;
import com.article.common.utils.FormatUtils;
import com.article.common.utils.LogUtils;
import com.article.common.utils.ScreenUtils;
import com.article.common.utils.SharedPreferencesUtils;
import com.article.common.utils.SnackBarUtils;
import com.article.common.utils.StatusBarCompat;
import com.article.core.book.adapter.BookTocListAdapter;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.BookResource;
import com.article.core.book.bean.ChapterRead;
import com.article.core.book.bean.Recommend;
import com.article.core.book.bean.support.BookDownloadQueue;
import com.article.core.book.contract.BookReadContract;
import com.article.core.book.manager.CacheManager;
import com.article.core.book.manager.CollectionsManager;
import com.article.core.book.manager.EventManager;
import com.article.core.book.manager.SettingManager;
import com.article.core.book.presenter.BookReadPresenter;
import com.article.core.book.service.BookDownloadService;
import com.article.core.book.ui.fragment.BookResourceDialogFragment;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;
import com.article.widget.read.CustomReadView;
import com.article.widget.read.OnReadStateChangeListener;
import com.article.widget.read.OverlappedWidget;
import com.article.widget.read.PageWidget;
import com.article.widget.read.ThemeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;

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

    //顶部栏
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

    //下载进度条
    @BindView(R.id.pbBookDownload)
    ProgressBar mPbBookDownload;

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

    private BookResourceDialogFragment mDialogFragment;
    private String mHostName;

    //小说目录列表弹出窗
    private ListPopupWindow mTocListPopupWindow;

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
        statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary);
        return R.layout.activity_read;
    }

    @Override
    public void initData() {
        mPresenter.attachView(this);
        mBooks = (Recommend.RecommendBooks) getIntent().getSerializableExtra(INTENT_BEAN);
        bookId = mBooks._id;
        isFromSd = getIntent().getBooleanExtra(INTENT_SD, false);
        mTvBookReadTocTitle.setText(mBooks.title);
        CollectionsManager.getInstance().setRecentReadingTime(bookId);
        Flowable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> EventManager.refreshCollectionList());
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
        mPresenter.attachView(this);

        decodeView = getWindow().getDecorView();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlBookReadTop.getLayoutParams();
        params.topMargin = ScreenUtils.getStatusBarHeight(this) - 2;
        mLlBookReadTop.setLayoutParams(params);
        if (statusBarColor == 0) {
            statusBarView = StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        } else if (statusBarColor != -1) {
            statusBarView = StatusBarCompat.compat(this, statusBarColor);
        }
        initTocPop();
        initReadSet();
        initPagerWidget();
        hideStatusBar();

        mPresenter.getBookMixAToc(bookId, "chapters");
        mPresenter.getBookResource(bookId);
    }

    private BookTocListAdapter mTocListAdapter;

    /**
     * 初始化小说目录弹出框
     */
    private void initTocPop() {
        mTocListAdapter = new BookTocListAdapter(this, mChapterList, bookId, currentChapter);
        mTocListPopupWindow = new ListPopupWindow(this);
        mTocListPopupWindow.setAdapter(mTocListAdapter);
        mTocListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mTocListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mTocListPopupWindow.setAnchorView(mLlBookReadTop);
        mTocListPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            mTocListPopupWindow.dismiss();
            currentChapter = position + 1;
            startRead = false;
            mTocListAdapter.setCurrentChapter(currentChapter);
            readCurrentChapter();
            hideReadBar();
        });
        mTocListPopupWindow.setOnDismissListener(() -> {
            gone(mTvBookReadTocTitle);
            visible(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadSource);
        });
    }

    /**
     * 初始化阅读设置
     */
    private void initReadSet() {
        curTheme = SettingManager.getInstance().getReadTheme();
        ThemeManager.setReaderTheme(curTheme, mRlBookReadRoot);
    }

    private void readCurrentChapter() {
        if (CacheManager.getInstance().getChapterFile(bookId, currentChapter) != null) {
            showChapterRead(null, currentChapter);
        } else {
            mPresenter.getChapterRead(mChapterList.get(currentChapter - 1).link, currentChapter);
        }
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

        if (SharedPreferencesUtils.getInstance().getBoolean(Constant.IS_NIGHT, false)) {
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_night),
                    ContextCompat.getColor(this, R.color.colorPrimary));
        }
        mFlReadWidget.removeAllViews();
        mFlReadWidget.addView(mPageWidget);
    }


    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showBookToc(List<BookMixAToc.mixToc.Chapters> list) {
        mChapterList.clear();
        mChapterList.addAll(list);
        readCurrentChapter();

        String link = list.get(0).link;
        String[] split = link.split("/+");
        String host = split[1];
        if (host.contains("www")) {
            mHostName = host.substring(4, split.length);
        } else {
            mHostName = host;
        }
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
    public void showBookResource(BookResource bookResource) {
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


    /***************按钮事件*****************/
    /**
     * 返回键事件
     */
    @OnClick(R.id.ivBack)
    public void onBackClick() {
        finish();
    }

    /**
     * 朗读
     */
    @OnClick(R.id.tvBookReadReading)
    public void onBookReadReadingClick() {
        SnackBarUtils.showSnackbar(mFlReadWidget, "正在努力开发中！");
    }

    /**
     * 社区
     */
    @OnClick(R.id.tvBookReadCommunity)
    public void onBookReadCommunityClick() {
        SnackBarUtils.showSnackbar(mFlReadWidget, "正在努力开发中！");
    }

    /**
     * 实现换源
     */
    @OnClick(R.id.tvBookReadSource)
    public void onBookResourceClick() {
//        if (mDialogFragment == null) {
//            mDialogFragment = BookResourceDialogFragment.newInstance();
//        }
//        mDialogFragment.show(getSupportFragmentManager(), "bookResource");
//        BookResourceActivity.startActivity(this, mBooks._id, mHostName);
        SnackBarUtils.showSnackbar(mFlReadWidget, "正在努力开发中！");
    }

    /**
     * 查看简介
     */
    @OnClick(R.id.tvBookReadIntroduce)
    public void onBookReadIntroduceClick() {
        gone(rlReadAaSet, mRlReadMark);
        BookDetailActivity.startActivity(this, bookId);
    }

    /**
     * 设置按钮
     */
    @OnClick(R.id.tvBookReadSettings)
    public void onBookReadSettingClick() {
        if (isVisible(mTvBookReadSettings)) {
            if (isVisible(rlReadAaSet)) {
                gone(rlReadAaSet);
            } else {
                visible(rlReadAaSet);
                gone(mRlReadMark);
            }
        }
    }

    /**
     * 下载
     */
    @OnClick(R.id.tvBookReadDownload)
    public void onBookReadDownloadClick() {
        gone(rlReadAaSet);
        new AlertDialog.Builder(this)
                .setTitle("想要缓存多少章？")
                .setItems(new String[]{"后面五十章", "后面全部", "全部"}, (dialog, which) -> {
                    switch (which) {
                        case 0://缓存五十章
                            BookDownloadService.post(new BookDownloadQueue(bookId, mChapterList, currentChapter + 1, currentChapter + 50));
                            break;
                        case 1://缓存后面全部
                            break;
                        case 2://缓存全部
                            break;
                    }
                }).create().show();
    }

    /**
     * 书签
     */
    @OnClick(R.id.tvBookMark)
    public void onBookMarkClick() {
        if (isVisible(mLlBookReadBottom)) {
            if (isVisible(mRlReadMark)) {
                gone(mRlReadMark);
            } else {
                gone(rlReadAaSet);
//                updateMark();
                visible(mRlReadMark);
            }
        }
    }


    /**
     * 显示目录
     */
    @OnClick(R.id.tvBookReadToc)
    public void onBookReadTocClick() {
        gone(mLlBookReadBottom, mRlReadMark);
        if (!mTocListPopupWindow.isShowing()) {
            visible(mTvBookReadTocTitle);
            gone(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadSource, mTvBookReadIntroduce);
            mTocListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mTocListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mTocListPopupWindow.show();
            mTocListPopupWindow.setSelection(currentChapter - 1);
            mTocListPopupWindow.getListView()
                    .setFastScrollEnabled(true);
        }
    }


    /***************按钮事件*****************/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mTocListPopupWindow != null && mTocListPopupWindow.isShowing()) {
                    mTocListPopupWindow.dismiss();
//                    gone(mTvBookReadTocTitle);
                    gone(mLlBookReadTop);
                    visible(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadSource);
                    return true;
                } else if (isVisible(rlReadAaSet)) {
                    gone(rlReadAaSet);
                    return true;
                } else if (isVisible(mLlBookReadBottom)) {
                    hideReadBar();
                    return true;
                } else if (!CollectionsManager.getInstance().isCollected(bookId)) {
                    showJoinBookShelfDialog(mBooks);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    //显示询问是否将小说加入书架
    private void showJoinBookShelfDialog(Recommend.RecommendBooks books) {
        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.book_read_add_book))
                .setMessage(getString(R.string.book_read_would_you_like_to_add_this_to_the_book_shelf))
                .setPositiveButton(getString(R.string.book_read_join_the_book_shelf), (dialog, which) -> {
                    dialog.dismiss();
                    books.recentReadingTime = FormatUtils.getCurrentTimeString(FormatUtils.FORMAT_DATE_TIME);
                    CollectionsManager.getInstance().add(books);
                    finish();
                })
                .setNegativeButton(getString(R.string.book_read_not), (dialog2, which) -> {
                    dialog2.dismiss();
                    finish();
                }).create().show();
    }

    private class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
            LogUtils.i("onChapterChanged:" + chapter);
            currentChapter = chapter;
            mTocListAdapter.setCurrentChapter(currentChapter);
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
