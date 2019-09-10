package xuk.imaginary.gui.gank.meizhi;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.databinding.FragmentMeizhiBinding;
import xuk.imaginary.gui.MainActivity;
import xuk.imaginary.gui.gank.show.GankActivity;
import xuk.imaginary.common.DateUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class MeizhiFragment extends Fragment {
    private static final String TAG = "MeizhiFragment";

    public static MeizhiFragment getInstance() {
        return new MeizhiFragment();
    }

    private MeiZhiAdapter meiZhiAdapter = new MeiZhiAdapter(new ArrayList<>());
    private FragmentMeizhiBinding binding;
    private MeiZhiViewModule meiZhiViewModule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        binding = FragmentMeizhiBinding.inflate(inflater, container, false);
        meiZhiViewModule = MainActivity.obtainViewModel(getActivity());
        binding.setViewmodule(meiZhiViewModule);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(meiZhiAdapter);

        meiZhiAdapter.setOnItemClickListener((adapter, v, position) -> {
            BaseBean fl = (BaseBean) adapter.getData().get(position);
            Log.d(TAG, "position:" + position + ", data:" + fl.toString());
            String dateString = DateUtils.getDate(fl.getPublishedAt());
            Intent intent = new Intent(getActivity(), GankActivity.class);
            intent.putExtra("date", dateString);
            intent.putExtra("image", fl.getUrl());
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, "shareObject");
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        });
        meiZhiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        meiZhiAdapter.isFirstOnly(true);

        binding.materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                meiZhiViewModule.getList(true);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefresh, 1000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                meiZhiViewModule.getList(false);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefreshLoadMore, 1000);
            }
        });
        meiZhiViewModule.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        meiZhiViewModule.end();
    }

}
