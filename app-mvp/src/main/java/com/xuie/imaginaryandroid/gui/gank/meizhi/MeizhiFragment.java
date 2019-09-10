package xuk.imaginary.gui.gank.meizhi;

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
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import xuk.imaginary.R;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.data.source.GankRepository;
import xuk.imaginary.gui.gank.show.GankActivity;
import xuk.imaginary.common.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static xuk.imaginary.common.Utils.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * @author xuie
 */
public class MeizhiFragment extends Fragment implements MeizhiContract.View {
    private static final String TAG = "MeizhiFragment";

    public static MeizhiFragment getInstance() {
        return new MeizhiFragment();
    }

    private MeizhiContract.Presenter mPresenter;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.material_refresh) MaterialRefreshLayout materialRefresh;
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

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(meiZhiAdapter);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
        unbinder.unbind();
    }
}
