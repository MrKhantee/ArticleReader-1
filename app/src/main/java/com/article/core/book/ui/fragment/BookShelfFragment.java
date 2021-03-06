package com.article.core.book.ui.fragment;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.common.utils.SnackBarUtils;
import com.article.core.book.adapter.BookShelfAdapter;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.Recommend;
import com.article.core.book.bean.support.RefreshCollectionListEvent;
import com.article.core.book.contract.BookShelfContract;
import com.article.core.book.manager.CollectionsManager;
import com.article.core.book.presenter.BookShelfPresenter;
import com.article.core.book.ui.activity.BookDetailActivity;
import com.article.core.book.ui.activity.BookReadActivity;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.article.R.id.emptyView;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */
public class BookShelfFragment extends BaseFragment implements
        BookShelfContract.View, BookShelfAdapter.OnItemLongClickListener {

    @BindView(R.id.book_shelf_rv)
    RecyclerView mBookShelfRv;
    @BindView(emptyView)
    LinearLayout mEmptyView;
    @BindView(R.id.book_shelf_srl)
    SwipeRefreshLayout mBookShelfSrl;
    //全选布局
    @BindView(R.id.llBatchManagement)
    LinearLayout mBatchManager;
    //全选
    @BindView(R.id.tvSelectAll)
    TextView mSelectALl;
    //删除
    @BindView(R.id.tvDelete)
    TextView mDelete;

    private boolean isSelectAll = false;

    private List<Recommend.RecommendBooks> mCollectionBooks;
    private BookShelfAdapter mAdapter;


    @Inject
    BookShelfPresenter mPresenter;

    public static BookShelfFragment newInstance() {
        BookShelfFragment fragment = new BookShelfFragment();
        return fragment;
    }


    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }


    @Override
    public void initData() {
        mCollectionBooks = new ArrayList<>();
        mAdapter = new BookShelfAdapter(mContext, mCollectionBooks);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                mAdapter.notifyDataSetChanged();
                if (isVisible(mBatchManager)) {
                    goneBatchManagerLayoutAndRefreshUI();
                    return true;
                }
            }
            return false;
        });
    }

    /**
     * 隐藏批量管理布局，刷新UI
     */
    private void goneBatchManagerLayoutAndRefreshUI() {
        if (mAdapter == null) {
            return;
        }
        gone(mBatchManager);
        for (Recommend.RecommendBooks bean : mAdapter.getAll()) {
            bean.showCheckBox = false;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void configViews() {
        mBookShelfRv.setHasFixedSize(true);
        mBookShelfRv.setLayoutManager(new LinearLayoutManager(mContext));
        mBookShelfRv.setAdapter(mAdapter);
        mBookShelfSrl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mBookShelfSrl.measure(0, 0);
        mBookShelfSrl.setRefreshing(true);
        //条目点击监听
        mAdapter.setItemClickListener((view, position) -> {
            if (isVisible(mBatchManager)) {
                return;
            }
            Recommend.RecommendBooks books = mCollectionBooks.get(position);
            BookReadActivity.startActivity(mContext, books, true);
        });
        //悬浮按钮监听
//        mFab.setOnClickListener(v -> ((BookActivity) mActivity).setCurrentItem(1));
        //长按监听
        mAdapter.setItemLongClickListener(this);
        mBookShelfSrl.setOnRefreshListener(this::onRefreshing);

//        mPresenter.getCollectionBook();
        onRefreshing();

        mAdapter.setOnMoreIVClickListener(position -> showLongClickDialog(position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_shelf;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public boolean onItemLongClick(int position) {
        if (isVisible(mBatchManager)) {
            return false;
        }
        showLongClickDialog(position);
        return false;
    }

    /**
     * 显示长按监听窗口
     */
    private void showLongClickDialog(int position) {
        boolean isTop = CollectionsManager.getInstance().isTop(mCollectionBooks.get(position)._id);
        String[] items;
        DialogInterface.OnClickListener listener;
        if (mCollectionBooks.get(position).isFromSD) {
            //当小说的本地的时候
            items = getResources().getStringArray(R.array.book_shelf_item_long_click_choice_local);
            listener = (dialog, which) -> {
                switch (which) {
                    case 0://置顶、取消置顶
                        break;
                    case 1://删除
                        break;
                    case 2://批量管理
                        break;
                }
                dialog.dismiss();
            };
        } else {
            //小说不是本地书籍
            items = getResources().getStringArray(R.array.book_shelf_item_long_click_choice);
            listener = (dialog, which) -> {
                switch (which) {
                    case 0://置顶、取消置顶
                        CollectionsManager.getInstance().top(mCollectionBooks.get(position)._id, !isTop);
                        break;
                    case 1://书籍详情
                        BookDetailActivity.startActivity(mContext, mCollectionBooks.get(position)._id);
                        break;
                    case 2://移入养肥区
                        break;
                    case 3://缓存全本
                        break;
                    case 4://删除
                        List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                        Recommend.RecommendBooks item = mAdapter.getItem(position);
                        removeList.add(item);
                        showDeleteCacheDialog(removeList);
                        break;
                    case 5://批量管理
                        showBatchManagerLayout();
                        break;
                }
                dialog.dismiss();
            };
        }
        if (isTop) {
            items[0] = getResources().getString(R.string.cancel_top);
        }
        new AlertDialog.Builder(mContext)
                .setTitle(mCollectionBooks.get(position).title)
                .setItems(items, listener)
                .setNegativeButton(null, null)
                .create().show();
    }

    /**
     * 清除本地缓存的对话框
     *
     * @param removeList
     */
    private void showDeleteCacheDialog(List<Recommend.RecommendBooks> removeList) {
        boolean selected[] = {true};
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.remove_selected_book))
                .setMultiChoiceItems(new String[]{mContext.getString(R.string.delete_local_cache)}, selected,
                        (dialog, which, isChecked) -> selected[0] = isChecked)
                .setNegativeButton(mContext.getString(R.string.cancel), null)
                .setPositiveButton(mContext.getString(R.string.confirm), (dialog, which) -> {
                    dialog.dismiss();
                    new AsyncTask<String, String, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            CollectionsManager.getInstance().removeSome(removeList, selected[0]);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            SnackBarUtils.showSnackbar(mBookShelfRv, "成功移除书籍");
                            for (Recommend.RecommendBooks bean : removeList) {
                                mAdapter.remove(bean);
                            }
                        }
                    }.execute();
                })
                .create().show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCollectionList(RefreshCollectionListEvent event) {
        mBookShelfSrl.setRefreshing(true);
        onRefreshing();
    }

    /**
     * 显示批量管理布局
     */
    private void showBatchManagerLayout() {
        visible(mBatchManager);
        for (Recommend.RecommendBooks bean : mAdapter.getAll()) {
            bean.showCheckBox = true;
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 全选事件点击
     */
    @OnClick(R.id.tvSelectAll)
    public void selectAll() {
        isSelectAll = !isSelectAll;
        mSelectALl.setText(isSelectAll ? mContext.getString(R.string.cancel_selected_all) : mContext.getString(R.string.selected_all));
        for (Recommend.RecommendBooks bean : mAdapter.getAll()) {
            bean.isSeleted = isSelectAll;
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 删除选中的小说
     */
    @OnClick(R.id.tvDelete)
    public void deleteSelected() {
        List<Recommend.RecommendBooks> removeList = new ArrayList<>();
        for (Recommend.RecommendBooks bean : mAdapter.getAll()) {
            if (bean.isSeleted) {
                removeList.add(bean);
            }
        }
        if (removeList.isEmpty()) {
            SnackBarUtils.showSnackbar(mDelete, mContext.getString(R.string.has_not_selected_delete_book));
        } else {
            showDeleteCacheDialog(removeList);
            goneBatchManagerLayoutAndRefreshUI();
        }
    }

    /**
     * 刷新数据
     */
    private void onRefreshing() {
        gone(mBatchManager);
        List<Recommend.RecommendBooks> list = CollectionsManager.getInstance().getCollectionListBySort();
        if (list!=null&&list.size() > 0) {
            mBookShelfRv.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.INVISIBLE);
            mAdapter.clear();
            mAdapter.addAll(list);
            mAdapter.notifyDataSetChanged();
            mBookShelfSrl.setRefreshing(false);
        } else {
            mBookShelfSrl.setRefreshing(false);
            mBookShelfRv.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showError() {
        mBookShelfSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mBookShelfSrl.setRefreshing(false);
    }

    @Override
    public void showCollectionBook(List<Recommend.RecommendBooks> collectionBooks) {
        mCollectionBooks.clear();
        mCollectionBooks.addAll(collectionBooks);
        mAdapter.addAll(collectionBooks);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showChapter(String bookId, List<BookMixAToc.mixToc.Chapters> mChapters) {

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

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

}
