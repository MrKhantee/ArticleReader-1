package com.article.core.book.ui.activity;

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
import com.article.di.component.AppComponent;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/19.
 * Desc：
 */

public class BookReadActivity extends BaseActivity {

    @BindView(R.id.flReadWidget)
    FrameLayout mFlReadWidget;
    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tvBookReadTocTitle)
    TextView mTvBookReadTocTitle;
    @BindView(R.id.tvBookReadReading)
    TextView mTvBookReadReading;
    @BindView(R.id.tvBookReadCommunity)
    TextView mTvBookReadCommunity;
    @BindView(R.id.tvBookReadIntroduce)
    TextView mTvBookReadIntroduce;
    @BindView(R.id.tvBookReadSource)
    TextView mTvBookReadSource;
    @BindView(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;
    @BindView(R.id.tvDownloadProgress)
    TextView mTvDownloadProgress;
    @BindView(R.id.ivBrightnessMinus)
    ImageView mIvBrightnessMinus;
    @BindView(R.id.seekbarLightness)
    SeekBar mSeekbarLightness;
    @BindView(R.id.ivBrightnessPlus)
    ImageView mIvBrightnessPlus;
    @BindView(R.id.tvFontsizeMinus)
    TextView mTvFontsizeMinus;
    @BindView(R.id.seekbarFontSize)
    SeekBar mSeekbarFontSize;
    @BindView(R.id.tvFontsizePlus)
    TextView mTvFontsizePlus;
    @BindView(R.id.cbVolume)
    CheckBox mCbVolume;
    @BindView(R.id.cbAutoBrightness)
    CheckBox mCbAutoBrightness;
    @BindView(R.id.gvTheme)
    GridView mGvTheme;
    @BindView(R.id.tvAddMark)
    TextView mTvAddMark;
    @BindView(R.id.tvClear)
    TextView mTvClear;
    @BindView(R.id.lvMark)
    ListView mLvMark;
    @BindView(R.id.rlReadMark)
    LinearLayout mRlReadMark;
    @BindView(R.id.tvBookReadMode)
    TextView mTvBookReadMode;
    @BindView(R.id.tvBookReadSettings)
    TextView mTvBookReadSettings;
    @BindView(R.id.tvBookReadDownload)
    TextView mTvBookReadDownload;
    @BindView(R.id.tvBookMark)
    TextView mTvBookMark;
    @BindView(R.id.tvBookReadToc)
    TextView mTvBookReadToc;
    @BindView(R.id.llBookReadBottom)
    LinearLayout mLlBookReadBottom;
    @BindView(R.id.rlBookReadRoot)
    RelativeLayout mRlBookReadRoot;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_read;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void configViews() {

    }


}
