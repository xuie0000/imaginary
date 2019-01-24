package com.xuie.imaginary.gui.nets.news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.xuie.imaginary.R;
import com.xuie.imaginary.app.App;
import com.xuie.imaginary.base.BaseFragment;
import com.xuie.imaginary.data.NetsSummary;
import com.xuie.imaginary.data.source.NetsRepository;
import com.xuie.imaginary.databinding.FragmentNewsListBinding;
import com.xuie.imaginary.gui.nets.detail.NetsOneActivity;
import com.xuie.imaginary.gui.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link BaseFragment} subclass.
 *
 * @author xuie
 */
public class NewsListFragment extends BaseFragment implements NewsListContract.View {
    private static final String TAG = "NewsListFragment";

    public static NewsListFragment getInstance() {
        return new NewsListFragment();
    }

    private NewsListContract.Presenter mPresenter = new NewsListPresenter(NetsRepository.getInstance(), this);
    private NewsListAdapter newsListAdapter = new NewsListAdapter(new ArrayList<>());

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
                    view.findViewById(R.id.title),
                    ns.getPostid(),
                    ns.getImgsrc());
        });
    }
}
