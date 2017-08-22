package com.xuie.imaginaryandroid.gui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.gzoomswiperefresh.GZoomSwipeRefresh;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.NetsSummary;
import com.xuie.imaginaryandroid.data.source.NETSRepository;
import com.xuie.imaginaryandroid.gui.detail.NetsOneActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment implements NewsListContract.View,
        GZoomSwipeRefresh.OnBottomRefreshListener, GZoomSwipeRefresh.OnRefreshListener {
    private static final String TAG = "NewsListFragment";

    public static NewsListFragment getInstance() {
        return new NewsListFragment();
    }

    @BindView(R.id.recycle_view) RecyclerView recycleView;
    @BindView(R.id.swipe_refresh) GZoomSwipeRefresh swipeRefresh;
    Unbinder unbinder;

    private NewsListContract.Presenter mPresenter;
    private NewsListAdapter newsListAdapter = new NewsListAdapter(null);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NewsListPresenter(NETSRepository.getInstance(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        // 设置下拉时旋转的三种颜色
        swipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(this);
        // 设置底部下拉时的三种颜色
        swipeRefresh.setBottomColorSchemeColors(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        swipeRefresh.setOnBottomRefreshListenrer(this);

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(newsListAdapter);
        mPresenter.subscribe();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(NewsListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void addList(boolean isRefresh, List<NetsSummary> netsSummaries) {
        if (isRefresh)
            newsListAdapter.replaceData(new ArrayList<>());
//        Log.d(TAG, netsSummaries.toString());
        newsListAdapter.addData(netsSummaries);
        newsListAdapter.setOnItemClickListener((adapter, view, position) -> {
            NetsSummary ns = netsSummaries.get(position);
            NetsOneActivity.newIntent(getActivity(), view, ns.getPostid(), ns.getImgsrc());
        });
        cancelRefresh();
    }

    private void cancelRefresh() {
        // 让子弹飞一会儿.
        if (swipeRefresh != null) {
            swipeRefresh.postDelayed(() -> {
                synchronized (this) {
                    if (swipeRefresh == null)
                        return;
                    if (swipeRefresh.isRefreshing())
                        swipeRefresh.setRefreshing(false);
                    swipeRefresh.setBottomRefreshing(false);
                }
            }, 1000);
        }
    }

    /**
     * {@link com.xuie.gzoomswiperefresh.GZoomSwipeRefresh.OnRefreshListener}
     */
    @Override
    public void onRefresh() {
        mPresenter.getList(true);
    }

    /**
     * {@link com.xuie.gzoomswiperefresh.GZoomSwipeRefresh.OnBottomRefreshListener}
     */
    @Override
    public void onBottomRefresh() {
        mPresenter.getList(false);
    }
}
