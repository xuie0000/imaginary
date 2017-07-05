package com.xuie.imaginaryandroid.gui.meizhi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.gzoomswiperefresh.GZoomSwipeRefresh;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.MeiZhi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFragment extends Fragment implements GankContract.View, GZoomSwipeRefresh.OnRefreshListener, GZoomSwipeRefresh.OnBottomRefreshListener {
    private static final String TAG = "GankFragment";

    private GankContract.Presenter mPresenter;
    @BindView(R.id.recycle_view) RecyclerView recycleView;
    @BindView(R.id.swipe_refresh) GZoomSwipeRefresh swipeRefresh;
    Unbinder unbinder;

    private MeiZhiAdapter meiZhiAdapter = new MeiZhiAdapter(null);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        unbinder = ButterKnife.bind(this, view);

        swipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setBottomColorSchemeColors(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        swipeRefresh.setOnBottomRefreshListenrer(this);

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void addList(boolean isRefresh, List<MeiZhi> meiZhis) {
        if (isRefresh)
            meiZhiAdapter.replaceData(null);
        meiZhiAdapter.addData(meiZhis);
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

    /**
     * {@link com.xuie.gzoomswiperefresh.GZoomSwipeRefresh.OnRefreshListener}
     */
    @Override
    public void onRefresh() {

    }

    /**
     * {@link com.xuie.gzoomswiperefresh.GZoomSwipeRefresh.OnBottomRefreshListener}
     */
    @Override
    public void onBottomRefresh() {

    }
}
