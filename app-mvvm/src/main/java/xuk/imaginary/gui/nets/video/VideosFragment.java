package xuk.imaginary.gui.nets.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import xuk.imaginary.databinding.FragmentVideosBinding;
import xuk.imaginary.gui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class VideosFragment extends Fragment {
    private static final String TAG = "VideosFragment";
    public static final String VIDEO_TYPE_ID = "type";
    public static final String VIDEO_TYPE_NAME = "name";

    public static VideosFragment getInstance(String typeName, String typeId) {
        VideosFragment fragment = new VideosFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_TYPE_NAME, typeName);
        bundle.putString(VIDEO_TYPE_ID, typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mVideoType;
    private VideosAdapter videosAdapter = new VideosAdapter(new ArrayList<>());
    private FragmentVideosBinding mBinding;
    private VideosViewModule videosViewModule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentVideosBinding.inflate(inflater, container, false);
        videosViewModule = MainActivity.obtainVideosViewModel(getActivity());
        mBinding.setViewModule(videosViewModule);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVideoType = getArguments().getString(VIDEO_TYPE_ID);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(videosAdapter);

        videosAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        videosViewModule.getList(mVideoType, true);

        mBinding.materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                videosViewModule.getList(mVideoType, true);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefresh, 1000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                videosViewModule.getList(mVideoType, false);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefreshLoadMore, 1000);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
