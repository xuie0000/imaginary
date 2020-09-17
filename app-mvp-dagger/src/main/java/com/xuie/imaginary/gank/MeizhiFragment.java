package xuk.imaginary.gank;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.Fragment;
import androidx.appcompat.widget.RecyclerView;
import androidx.appcompat.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import xuk.imaginary.R;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.di.ActivityScoped;
import xuk.imaginary.gankdate.GankDayActivity;
import xuk.imaginary.common.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
@ActivityScoped
public class MeizhiFragment extends DaggerFragment implements MeizhiContract.View {
    private static final String TAG = "MeizhiFragment";

    @Inject
    MeizhiContract.Presenter mPresenter;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.material_refresh) MaterialRefreshLayout materialRefresh;
    Unbinder unbinder;
    private MeiZhiAdapter meiZhiAdapter = new MeiZhiAdapter(null);

    @Inject
    public MeizhiFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizhi, container, false);
        unbinder = ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView");

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(meiZhiAdapter);
        meiZhiAdapter.setOnItemClickListener((adapter, view1, position) -> {
            Log.d(TAG, "position:" + position);
            BaseBean fl = (BaseBean) adapter.getData().get(position);
            Log.d("MeizhiFragment", fl.toString());
            String dateString = DateUtils.getDate(fl.getPublishedAt());
            Intent intent = new Intent(getActivity(), GankDayActivity.class);
            intent.putExtra("date", dateString);
            intent.putExtra("image", fl.getUrl());
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view1, "shareObject");
            ActivityCompat.startActivity(Objects.requireNonNull(getActivity()), intent, options.toBundle());
        });
        meiZhiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        meiZhiAdapter.isFirstOnly(true);

        materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                mPresenter.getList(true);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefresh, 1000);
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                mPresenter.getList(false);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefreshLoadMore, 1000);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void addList(boolean isRefresh, List<BaseBean> meiZhis) {
        if (isRefresh) {
            meiZhiAdapter.replaceData(new ArrayList<>());
        }
//        Log.d(TAG, meiZhis.toString())
        meiZhiAdapter.addData(meiZhis);
    }
}
