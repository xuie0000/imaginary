package com.xuie.imaginaryandroid.gui.nets.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.app.App;
import com.xuie.imaginaryandroid.base.BaseFragment;
import com.xuie.imaginaryandroid.data.NetsSummary;
import com.xuie.imaginaryandroid.data.source.NetsRepository;
import com.xuie.imaginaryandroid.databinding.FragmentNewsListBinding;
import com.xuie.imaginaryandroid.gui.nets.detail.NetsOneActivity;
import com.xuie.imaginaryandroid.gui.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class NewsListFragment extends BaseFragment implements NewsListContract.View {
    private static final String TAG = "NewsListFragment";

    public static NewsListFragment getInstance() {
        return new NewsListFragment();
    }

    private NewsListContract.Presenter mPresenter = new NewsListPresenter(NetsRepository.getInstance(), this);
    private NewsListAdapter newsListAdapter = new NewsListAdapter(null);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    private FragmentNewsListBinding mBinding;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        mBinding = getDataBinding();
        mBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.recyclerView.setAdapter(newsListAdapter);
        newsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mPresenter.subscribe();

        mBinding.materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void addList(boolean isRefresh, List<NetsSummary> netsSummaries) {
        if (isRefresh) {
            newsListAdapter.replaceData(new ArrayList<>());
        }
//        Log.d(TAG, netsSummaries.toString())
        newsListAdapter.addData(netsSummaries);
        newsListAdapter.setOnItemClickListener((adapter, view, position) -> {
            NetsSummary ns = (NetsSummary) adapter.getData().get(position);
            if (ns.getImgextra() != null && ns.getPhotosetID() != null) {
                Intent intent = new Intent(App.getContext(), WebViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String pv = ns.getPhotosetID();
                Log.d(TAG, "ns:" + ns.toString());
                Log.d(TAG, "pv:" + pv);

                pv = pv.replace("|", "/");
                intent.putExtra("url", "http://news.163.com/photoview/" + pv + ".html");
                App.getContext().startActivity(intent);
                return;
            }
            NetsOneActivity.newIntent(getActivity(),
                    view.findViewById(R.id.img_src),
                    view.findViewById(R.id.ltitle),
                    ns.getPostid(),
                    ns.getImgsrc());
        });
    }
}
