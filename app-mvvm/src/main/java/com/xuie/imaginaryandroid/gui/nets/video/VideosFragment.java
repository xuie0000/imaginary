package com.xuie.imaginaryandroid.gui.nets.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.base.BaseFragment;
import com.xuie.imaginaryandroid.data.VideoBean;
import com.xuie.imaginaryandroid.data.source.NetsRepository;
import com.xuie.imaginaryandroid.databinding.FragmentVideosBinding;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class VideosFragment extends BaseFragment implements VideosContract.View {

    public static final String VIDEO_TYPE_ID = "type";
    public static final String VIDEO_TYPE_NAME = "name";

    public static VideosFragment getInstance(String typeName, String typeId) {
        VideosFragment fragment = new VideosFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_TYPE_NAME, typeName);
        bundle.putString(VIDEO_TYPE_ID, typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mVideoType;
    private VideosContract.Presenter mPresenter = new VideosPresenter(NetsRepository.getInstance(), this);
    private VideosAdapter videosAdapter = new VideosAdapter(null);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        return view;
    }

    private FragmentVideosBinding mBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_videos;
    }

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        mBinding = getDataBinding();
        mVideoType = getArguments().getString(VIDEO_TYPE_ID);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(videosAdapter);

        videosAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        mPresenter.subscribe();
        mPresenter.getList(mVideoType, true);


        mBinding.materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mPresenter.getList(mVideoType, true);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefresh, 1000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                mPresenter.getList(mVideoType, false);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefreshLoadMore, 1000);
            }
        });
    }

    @Override
    protected void lazyInitData() {

    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void addList(boolean isRefresh, List<VideoBean> videoBeen) {
        if (isRefresh) {
            videosAdapter.replaceData(new ArrayList<>());
        }
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
    }
}
