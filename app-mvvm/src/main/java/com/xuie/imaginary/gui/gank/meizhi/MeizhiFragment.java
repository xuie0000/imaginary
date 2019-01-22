package com.xuie.imaginary.gui.gank.meizhi;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.xuie.imaginary.R;
import com.xuie.imaginary.base.BaseFragment;
import com.xuie.imaginary.data.BaseBean;
import com.xuie.imaginary.data.source.GankRepository;
import com.xuie.imaginary.databinding.FragmentMeizhiBinding;
import com.xuie.imaginary.gui.gank.show.GankActivity;
import com.xuie.imaginary.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class MeizhiFragment extends BaseFragment implements MeizhiContract.View {
    private static final String TAG = "MeizhiFragment";

    public static MeizhiFragment getInstance() {
        return new MeizhiFragment();
    }

    private MeizhiContract.Presenter mPresenter = new MeizhiPresenter(GankRepository.getInstance(), this);

    private MeiZhiAdapter meiZhiAdapter = new MeiZhiAdapter(new ArrayList<>());
    private FragmentMeizhiBinding binding;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meizhi;
    }

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onInit: ...");
        binding = getDataBinding();

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(meiZhiAdapter);
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

        binding.materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mPresenter.getList(true);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefresh, 1000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                mPresenter.getList(false);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefreshLoadMore, 1000);
            }
        });
    }

    @Override
    protected void lazyInitData() {
        mPresenter.subscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void addList(boolean isRefresh, List<BaseBean> meiZhis) {
        if (isRefresh) {
            meiZhiAdapter.replaceData(new ArrayList<>());
        }
        Log.d(TAG, meiZhis.toString());
        meiZhiAdapter.addData(meiZhis);
    }

}
