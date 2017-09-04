package com.xuie.imaginaryandroid.gui.meizhi;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xuie.gzoomswiperefresh.GZoomSwipeRefresh;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.BaseBean;
import com.xuie.imaginaryandroid.data.source.GankRepository;
import com.xuie.imaginaryandroid.gui.show.GankActivity;
import com.xuie.imaginaryandroid.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeizhiFragment extends Fragment implements MeizhiContract.View,
        GZoomSwipeRefresh.OnRefreshListener,
        GZoomSwipeRefresh.OnBottomRefreshListener {
    private static final String TAG = "MeizhiFragment";

    public static MeizhiFragment getInstance() {
        return new MeizhiFragment();
    }

    private MeizhiContract.Presenter mPresenter;
    @BindView(R.id.recycle_view) RecyclerView recycleView;
    @BindView(R.id.swipe_refresh) GZoomSwipeRefresh swipeRefresh;
    Unbinder unbinder;

    private MeiZhiAdapter meiZhiAdapter = new MeiZhiAdapter(null);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MeizhiPresenter(GankRepository.getInstance(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meizhi, container, false);
        unbinder = ButterKnife.bind(this, v);
        Log.d(TAG, "onCreateView");

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

        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(meiZhiAdapter);
        meiZhiAdapter.setOnItemClickListener((adapter, view, position) -> {
            Log.d(TAG, "position:" + position);
            BaseBean fl = (BaseBean) adapter.getData().get(position);
            Log.d("MeizhiFragment", fl.toString());
            String dateString = DateUtils.getDate(fl.getPublishedAt());
            Intent intent = new Intent(getActivity(), GankActivity.class);
            intent.putExtra("date", dateString);
            intent.putExtra("image", fl.getUrl());
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "shareObject");
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        });
        meiZhiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        meiZhiAdapter.isFirstOnly(true);

        mPresenter.subscribe();
        return v;
    }

    @Override
    public void setPresenter(MeizhiContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void addList(boolean isRefresh, List<BaseBean> meiZhis) {
        if (isRefresh)
            meiZhiAdapter.replaceData(new ArrayList<>());
//        Log.d(TAG, meiZhis.toString());
        meiZhiAdapter.addData(meiZhis);
        cancelRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
        unbinder.unbind();
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
