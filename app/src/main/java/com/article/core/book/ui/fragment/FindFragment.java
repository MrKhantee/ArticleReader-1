package com.article.core.book.ui.fragment;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.common.listener.OnRvItemClickListener;
import com.article.core.MainBean;
import com.article.core.book.adapter.FragmentFindAdapter;
import com.article.core.book.ui.activity.BookListActivity;
import com.article.core.book.ui.activity.TopRankActivity;
import com.article.di.component.AppComponent;
import com.article.widget.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */
public class FindFragment extends BaseFragment {

    @BindView(R.id.find_rv)
    RecyclerView mFindRv;
    private FragmentFindAdapter mAdapter;
    private List<MainBean> mBeanList;

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        return fragment;
    }


    @Override
    public void attachView() {

    }

    @Override
    public void initData() {
        mBeanList = new ArrayList<>();
        mBeanList.add(new MainBean("排行榜", R.drawable.find_ranking));
        mBeanList.add(new MainBean("分类", R.drawable.find_category));
        mBeanList.add(new MainBean("主题书单", R.drawable.find_topic));
    }

    @Override
    public void configViews() {
        mAdapter = new FragmentFindAdapter(getContext(), mBeanList, new OnItemClickListener());
        mFindRv.setHasFixedSize(true);
        mFindRv.addItemDecoration(new SupportDividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
        mFindRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mFindRv.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    class OnItemClickListener implements OnRvItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            switch (position) {
                case 0:
                    //排行榜
                    TopRankActivity.startActivity(getContext());
                    break;
                case 1:
                    //分类
                    break;
                case 2:
                    //书单
                    BookListActivity.startActivity(getContext());
                    break;
            }
        }
    }

}
