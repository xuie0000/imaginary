package com.xuie.imaginaryandroid.gui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.app.App;
import com.xuie.imaginaryandroid.data.NetsSummary;
import com.xuie.imaginaryandroid.data.source.NETSRepository;
import com.xuie.imaginaryandroid.gui.detail.NetsOneActivity;
import com.xuie.imaginaryandroid.gui.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment implements NewsListContract.View {
    private static final String TAG = "NewsListFragment";

    public static NewsListFragment getInstance() {
        return new NewsListFragment();
    }

    @BindView(R.id.recycler_view) RecyclerView recycleView;
    @BindView(R.id.material_refresh) MaterialRefreshLayout materialRefresh;
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
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(newsListAdapter);
        newsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mPresenter.subscribe();

        materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
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
