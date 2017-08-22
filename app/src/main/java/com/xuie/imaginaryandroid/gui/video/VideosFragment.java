package com.xuie.imaginaryandroid.gui.video;


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
import com.xuie.imaginaryandroid.data.VideoBean;
import com.xuie.imaginaryandroid.data.source.NETSRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment implements VideosContract.View, GZoomSwipeRefresh.OnRefreshListener, GZoomSwipeRefresh.OnBottomRefreshListener {

    public static final String VIDEO_TYPE = "type";

    public static VideosFragment getInstance(String type) {
        VideosFragment fragment = new VideosFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recycle_view) RecyclerView recycleView;
    @BindView(R.id.swipe_refresh) GZoomSwipeRefresh swipeRefresh;
    Unbinder unbinder;

    private String mVideoType;
    private VideosContract.Presenter mPresenter;
    private VideosAdapter videosAdapter = new VideosAdapter(null);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoType = getArguments().getString(VIDEO_TYPE);

        new VideosPresenter(NETSRepository.getInstance(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
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
        recycleView.setAdapter(videosAdapter);

        mPresenter.subscribe();
        mPresenter.getList(mVideoType, true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(VideosContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void addList(boolean isRefresh, List<VideoBean> videoBeen) {
        if (isRefresh)
            videosAdapter.replaceData(new ArrayList<>());
//        Log.d(TAG, videoBeen.toString());
        videosAdapter.addData(videoBeen);
//        videosAdapter.setOnItemClickListener((adapter, view, position) -> {
//            VideoBean vb = videoBeen.get(position);
//            NetsOneActivity.newIntent(getActivity(),
//                    view.findViewById(R.id.img_src),
//                    view.findViewById(R.id.ltitle),
//                    vb.getPostid(),
//                    vb.getImgsrc());
//        });
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

    @Override
    public void onRefresh() {
        mPresenter.getList(mVideoType, true);
    }

    @Override
    public void onBottomRefresh() {
        mPresenter.getList(mVideoType, false);
    }
}
