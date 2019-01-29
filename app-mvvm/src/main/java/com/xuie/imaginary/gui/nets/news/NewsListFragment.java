package com.xuie.imaginary.gui.nets.news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.xuie.imaginary.R;
import com.xuie.imaginary.app.App;
import com.xuie.imaginary.data.NetsSummary;
import com.xuie.imaginary.databinding.FragmentNewsListBinding;
import com.xuie.imaginary.gui.MainActivity;
import com.xuie.imaginary.gui.nets.detail.NetsOneActivity;
import com.xuie.imaginary.gui.web.WebViewActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class NewsListFragment extends Fragment {
    private static final String TAG = "NewsListFragment";

    public static NewsListFragment getInstance() {
        return new NewsListFragment();
    }

    private NewsListAdapter newsListAdapter = new NewsListAdapter(new ArrayList<>());

    private FragmentNewsListBinding mBinding;
    private NewsListViewModule newsListViewModule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentNewsListBinding.inflate(inflater, container, false);
        newsListViewModule = MainActivity.obtainNewsViewModel(getActivity());
        mBinding.setViewmodule(newsListViewModule);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        newsListViewModule.getList(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.recyclerView.setAdapter(newsListAdapter);
        newsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        mBinding.materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                newsListViewModule.getList(true);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefresh, 1000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                newsListViewModule.getList(false);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefreshLoadMore, 1000);
            }
        });
        newsListAdapter.setOnItemClickListener((adapter, v, position) -> {
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
                    v.findViewById(R.id.img_src),
                    v.findViewById(R.id.title),
                    ns.getPostid(),
                    ns.getImgsrc());
        });
    }

}
