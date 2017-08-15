package com.xuie.imaginaryandroid.gui.meizhi;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xuie.gzoomswiperefresh.GZoomSwipeRefresh;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.source.GankRepository;
import com.xuie.imaginaryandroid.data.福利;
import com.xuie.imaginaryandroid.gui.show.MeizhiActivity;
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
public class GankFragment extends Fragment implements GankContract.View,
        GZoomSwipeRefresh.OnRefreshListener,
        GZoomSwipeRefresh.OnBottomRefreshListener {
    private static final String TAG = "GankFragment";

    public static GankFragment getInstance() {
        return new GankFragment();
    }

    private GankContract.Presenter mPresenter;
    @BindView(R.id.recycle_view) RecyclerView recycleView;
    @BindView(R.id.swipe_refresh) GZoomSwipeRefresh swipeRefresh;
    Unbinder unbinder;

    private MeiZhiAdapter meiZhiAdapter = new MeiZhiAdapter(null);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GankPresenter(GankRepository.getInstance(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        unbinder = ButterKnife.bind(this, view);
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

        recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recycleView.setAdapter(meiZhiAdapter);
        meiZhiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "position:" + position);
                福利 fl = (福利) adapter.getData().get(position);
                Log.d("GankFragment", fl.toString());
                String dateString = DateUtils.getDate(fl.getPublishedAt());
                Intent intent = new Intent(getActivity(), MeizhiActivity.class);
                intent.putExtra("date", dateString);
                intent.putExtra("image", fl.getUrl());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "shareObject");
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });

        return view;
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void addList(boolean isRefresh, List<福利> meiZhis) {
        if (isRefresh)
            meiZhiAdapter.replaceData(new ArrayList<>());
//        Log.d(TAG, meiZhis.toString());
        meiZhiAdapter.addData(meiZhis);
        cancelRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
