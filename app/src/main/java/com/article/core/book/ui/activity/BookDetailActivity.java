package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.article.R;
import com.article.base.BaseMVPActivity;
import com.article.base.RealmHelper;
import com.article.common.Constant;
import com.article.common.listener.OnRvItemClickListener;
import com.article.common.utils.FormatUtils;
import com.article.common.utils.SnackBarUtils;
import com.article.core.book.adapter.RecommendBookListAdapter;
import com.article.core.book.bean.BookDetail;
import com.article.core.book.bean.Recommend;
import com.article.core.book.bean.RecommendBookList;
import com.article.core.book.bean.data.CollectionBook;
import com.article.core.book.bean.support.RefreshCollectionIconEvent;
import com.article.core.book.contract.BookDetailActivityContract;
import com.article.core.book.presenter.BookDetailActivityPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;
import com.article.widget.DrawableCenterButton;
import com.article.widget.FullyLinearLayoutManager;
import com.article.widget.GlideRoundTransform;
import com.article.widget.TagColor;
import com.article.widget.TagGroup;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BookDetailActivity extends BaseMVPActivity<BookDetailActivityPresenter>
        implements BookDetailActivityContract.View, OnRvItemClickListener {

    public static String INTENT_BOOK_ID = "bookId";

    @BindView(R.id.book_detail_tb)
    Toolbar mBookDetailTb;
    @BindView(R.id.book_detail_cover_iv)
    ImageView mBookDetailCoverIv;
    @BindView(R.id.book_detail_title_tv)
    TextView mBookDetailTitleTv;
    @BindView(R.id.book_detail_author_iv)
    TextView mBookDetailAuthorIv;
    @BindView(R.id.book_detail_category_tv)
    TextView mBookDetailCategoryTv;
    @BindView(R.id.book_detail_word_count_tv)
    TextView mBookDetailWordCountTv;
    @BindView(R.id.book_detail_later_update_tv)
    TextView mBookDetailLaterUpdateTv;
    @BindView(R.id.book_detail_lately_follower_tv)
    TextView mBookDetailLatelyFollowerTv;
    @BindView(R.id.book_detail_retention_ratio_tv)
    TextView mBookDetailRetentionRatioTv;
    @BindView(R.id.book_detail_serialize_word_count_tv)
    TextView mBookDetailSerializeWordCountTv;

    @BindView(R.id.book_detail_tag_tg)
    TagGroup mBookDetailTagTg;
    @BindView(R.id.book_detail_long_intro_tv)
    TextView mBookDetailLongIntroTv;
    @BindView(R.id.book_detail_recommend_book_list_tv)
    TextView mBookDetailRecommendBookListTv;
    @BindView(R.id.book_detail_recommend_book_list_rv)
    RecyclerView mBookDetailRecommendBookListRv;

    @BindView(R.id.book_detail_join_collection_btn)
    DrawableCenterButton mBookDetailJoinCollectionBtn;

    private String bookId;

    @Inject
    BookDetailActivityPresenter mPresenter;

    //标签
    private List<String> tagList = new ArrayList<>();
    private int times = 0;

    //设置小说收藏的信息
    private Recommend.RecommendBooks recommendBooks;
    private boolean isJoinedCollections = false;

    private boolean collapseLongIntro = true;
    private RecommendBookListAdapter mRecommendBookListAdapter;
    private List<RecommendBookList.RecommendBook> mBookList = new ArrayList<>();

    private RealmHelper mRealmHelper;

    public static void startActivity(Context context, String bookId) {
        context.startActivity(new Intent(context, BookDetailActivity.class).putExtra(INTENT_BOOK_ID, bookId));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initData() {
        bookId = getIntent().getStringExtra(INTENT_BOOK_ID);
        mRealmHelper = new RealmHelper(this);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public void initToolBar() {
        EventBus.getDefault().register(this);
        setSupportActionBar(mBookDetailTb);
        mBookDetailTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBookDetailTb.setTitle("小说详情");
    }

    @Override
    public void configViews() {
        showDialog();

        mRecommendBookListAdapter = new RecommendBookListAdapter(this, mBookList, this);
        mBookDetailRecommendBookListRv.setHasFixedSize(true);
        mBookDetailRecommendBookListRv.setNestedScrollingEnabled(false);
        mBookDetailRecommendBookListRv.setLayoutManager(new FullyLinearLayoutManager(this));
        mBookDetailRecommendBookListRv.setAdapter(mRecommendBookListAdapter);


        mPresenter.attachView(this);
        mPresenter.getBookDetail(bookId);
        mPresenter.getRecommendBookList(bookId, "3");

        mBookDetailTagTg.setOnTagClickListener(tag -> BooksByTagActivity.startActivity(BookDetailActivity.this, tag));

    }

    @Override
    public void showError() {
        dismissDialog();
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void showBookDetail(BookDetail bookDetail) {
        //加载小说图片
        Glide.with(mContext).load(Constant.BOOK_IMAGE_BASE_URL + bookDetail.getCover())
                .placeholder(R.drawable.cover_default)
                .transform(new GlideRoundTransform(mContext))
                .into(mBookDetailCoverIv);
        //设置小说标题
        mBookDetailTitleTv.setText(bookDetail.getTitle());
        //设置小说作者
        mBookDetailAuthorIv.setText(String.format(getString(R.string.book_detail_author), bookDetail.getAuthor()));
        //设置分类
        mBookDetailCategoryTv.setText(String.format(getString(R.string.book_detail_category), bookDetail.getCat()));
        //设置字数
        mBookDetailWordCountTv.setText(FormatUtils.formatWordCount(bookDetail.getWordCount()));
        //设置最后更新时间
        mBookDetailLaterUpdateTv.setText(FormatUtils.getDescriptionTimeFromDateString(bookDetail.getUpdated()));
        //设置追书人数
        mBookDetailLatelyFollowerTv.setText(String.valueOf(bookDetail.getLatelyFollower()));
        //设置读者留存率
        mBookDetailRetentionRatioTv.setText(TextUtils.isEmpty(bookDetail.getRetentionRatio()) ? "-" :
                String.format(getString(R.string.book_detail_retention_ratio), bookDetail.getRetentionRatio()));
        //设置日更新字数
        mBookDetailSerializeWordCountTv.setText(bookDetail.getSerializeWordCount() < 0 ? "-" :
                String.valueOf(bookDetail.getSerializeWordCount()));
        mBookDetailLongIntroTv.setText(bookDetail.getLongIntro());

        tagList.clear();
        tagList.addAll(bookDetail.getTags());
        times = 0;
        showHotWord();

        recommendBooks = new Recommend.RecommendBooks();
        recommendBooks._id = bookDetail.get_id();
        recommendBooks.cover = bookDetail.getCover();
        recommendBooks.title = bookDetail.getTitle();
        recommendBooks.lastChapter = bookDetail.getLastChapter();
        recommendBooks.updated = bookDetail.getUpdated();
        recommendBooks.author = bookDetail.getAuthor();

        refreshCollectionIcon();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCollectionIcon(RefreshCollectionIconEvent iconEvent) {
        refreshCollectionIcon();
    }

    /**
     * 刷新收藏图标
     */
    private void refreshCollectionIcon() {
        if (!mRealmHelper.isBookExit(recommendBooks._id)) {
            initCollectionIcon(true);
        } else {
            initCollectionIcon(false);
        }
    }

    /**
     * 更新收藏图标
     *
     * @param isCollected
     */
    private void initCollectionIcon(boolean isCollected) {
        if (isCollected) {
            mBookDetailJoinCollectionBtn.setText(R.string.book_detail_join_collection);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.book_detail_info_add_img);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBookDetailJoinCollectionBtn.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.shape_common_btn_solid_normal));
            mBookDetailJoinCollectionBtn.setCompoundDrawables(drawable, null, null, null);
            mBookDetailJoinCollectionBtn.postInvalidate();
            isJoinedCollections = true;
        } else {
            mBookDetailJoinCollectionBtn.setText(R.string.book_detail_remove_collection);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.book_detail_info_del_img);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBookDetailJoinCollectionBtn.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.btn_join_collection_pressed));
            mBookDetailJoinCollectionBtn.setCompoundDrawables(drawable, null, null, null);
            mBookDetailJoinCollectionBtn.postInvalidate();
            isJoinedCollections = true;
        }
    }

    /**
     * 显示关键字,最多显示8个
     */
    private void showHotWord() {
        int start, end;
        if (times < tagList.size() && times + 8 <= tagList.size()) {
            start = times;
            end = times + 8;
        } else if (times < tagList.size() - 1 && times + 8 > tagList.size()) {
            start = times;
            end = tagList.size() - 1;
        } else {
            start = 0;
            end = tagList.size() > 8 ? 8 : tagList.size();
        }
        times = end;
        if (end - start > 0) {
            List<String> batch = tagList.subList(start, end);
            List<TagColor> colors = TagColor.getRandomColors(batch.size());
            mBookDetailTagTg.setTags(colors, (String[]) batch.toArray(new String[batch.size()]));
        }
    }

    @Override
    public void showRecommendBookList(List<RecommendBookList.RecommendBook> recommendBookList) {
        if (recommendBookList != null && recommendBookList.size() > 0) {
            mBookDetailRecommendBookListRv.setVisibility(View.VISIBLE);
            mBookList.clear();
            mBookList.addAll(recommendBookList);
            mRecommendBookListAdapter.notifyDataSetChanged();
        } else {
            mBookDetailRecommendBookListRv.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 追更新点击监听
     */
    @OnClick(R.id.book_detail_join_collection_btn)
    public void onJoinCollectionClick() {
        if (mRealmHelper.isBookExit(recommendBooks._id)) {
            SnackBarUtils.showSnackbar(mBookDetailAuthorIv, String.format(getString(
                    R.string.book_detail_has_remove_the_book_shelf), recommendBooks.title));
            initCollectionIcon(true);
            mRealmHelper.deleteBook(recommendBooks._id);
        } else {
            CollectionBook collectionBook = new CollectionBook();
            collectionBook.set_id(recommendBooks._id);
            collectionBook.setLastChapter(recommendBooks.lastChapter);
            collectionBook.setUpdated(recommendBooks.updated);
            collectionBook.setTitle(recommendBooks.title);
            collectionBook.setCover(recommendBooks.cover);
            collectionBook.setAuthor(recommendBooks.author);
            mRealmHelper.addBook(collectionBook);
            SnackBarUtils.showSnackbar(mBookDetailAuthorIv,
                    String.format(getString(
                            R.string.book_detail_has_joined_the_book_shelf), recommendBooks.title));
            initCollectionIcon(false);
        }
//        if (isJoinedCollections) {
//            if (recommendBooks != null) {
//                CollectionsManager.getInstance().add(recommendBooks);
//
//            }
//        } else {
//            CollectionsManager.getInstance().remove(recommendBooks._id);
//
//        }
    }

    /**
     * 开始阅读按钮监听
     */
    @OnClick(R.id.book_detail_read_btn)
    public void onReadClick() {

    }

    @OnClick(R.id.book_detail_long_intro_tv)
    public void collapseLongIntro() {
        if (collapseLongIntro) {
            mBookDetailLongIntroTv.setMaxLines(20);
            collapseLongIntro = false;
        } else {
            mBookDetailLongIntroTv.setMaxLines(4);
            collapseLongIntro = true;
        }
    }

    /**
     * 更多书单
     */
    @OnClick(R.id.book_detail_more_list_tv)
    public void onMoreListClick() {
        RecommendBookListActivity.startActivity(this, bookId);
    }

    /**
     * 根据作者查询书籍
     */
    @OnClick(R.id.book_detail_author_iv)
    public void searchByAuthor() {
        String author = mBookDetailAuthorIv.getText().toString().trim();
        BooksByAuthorActivity.startActivity(BookDetailActivity.this, author);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * item点击事件回调
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        BookListDetailActivity.startActivity(this, mBookList.get(position).id);
    }
}
