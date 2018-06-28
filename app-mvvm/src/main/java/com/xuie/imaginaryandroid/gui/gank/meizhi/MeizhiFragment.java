package com.xuie.imaginaryandroid.gui.gank.meizhi;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class MeizhiFragment extends BaseFragment {
    private static final String TAG = "MeizhiFragment";

    public static MeizhiFragment getInstance() {
        return new MeizhiFragment();
    }

//    private MeizhiContract.Presenter mPresenter;

    private MeiZhiAdapter meiZhiAdapter = new MeiZhiAdapter(null);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meizhi;
    }

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = getDataBinding();

    }

    @Override
    protected void lazyInitData() {

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_meizhi, container, false);
//        unbinder = ButterKnife.bind(this, v);
//        Log.d(TAG, "onCreateView");
//
//        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(meiZhiAdapter);
//        meiZhiAdapter.setOnItemClickListener((adapter, view, position) -> {
//            Log.d(TAG, "position:" + position);
//            BaseBean fl = (BaseBean) adapter.getData().get(position);
//            Log.d("MeizhiFragment", fl.toString());
//            String dateString = DateUtils.getDate(fl.getPublishedAt());
//            Intent intent = new Intent(getActivity(), GankActivity.class);
//            intent.putExtra("date", dateString);
//            intent.putExtra("image", fl.getUrl());
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "shareObject");
//            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
//        });
//        meiZhiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        meiZhiAdapter.isFirstOnly(true);
//
//        materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
//            @Override
//            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                mPresenter.getList(true);
//                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefresh, 1000);
//            }
//
//            @Override
//            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                super.onRefreshLoadMore(materialRefreshLayout);
//                mPresenter.getList(false);
//                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefreshLoadMore, 1000);
//            }
//        });
//
//        mPresenter.subscribe();
//        return v;
//    }

//    @Override
//    public void addList(boolean isRefresh, List<BaseBean> meiZhis) {
//        if (isRefresh)
//            meiZhiAdapter.replaceData(new ArrayList<>());
////        Log.d(TAG, meiZhis.toString());
//        meiZhiAdapter.addData(meiZhis);
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        mPresenter.unsubscribe();
//        unbinder.unbind();
//    }
}
