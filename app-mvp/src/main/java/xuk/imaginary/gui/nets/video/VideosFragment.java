package xuk.imaginary.gui.nets.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import xuk.imaginary.R;
import xuk.imaginary.data.VideoBean;
import xuk.imaginary.data.source.NetsRepository;
import xuk.imaginary.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class VideosFragment extends Fragment implements VideosContract.View {

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

    private RecyclerView recycleView;
    private MaterialRefreshLayout materialRefresh;

    private String mVideoType;
    private VideosContract.Presenter mPresenter;
    private VideosAdapter videosAdapter = new VideosAdapter(null);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoType = getArguments().getString(VIDEO_TYPE_ID);

        new VideosPresenter(NetsRepository.getInstance(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        recycleView = view.findViewById(R.id.recycler_view);
        materialRefresh = view.findViewById(R.id.material_refresh);

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(videosAdapter);

        videosAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        mPresenter.subscribe();
        mPresenter.getList(mVideoType, true);


        materialRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mPresenter.getList(mVideoType, true);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefresh, 1000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                mPresenter.getList(mVideoType, false);
                materialRefreshLayout.postDelayed(materialRefreshLayout::finishRefreshLoadMore, 1000);
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(VideosContract.Presenter presenter) {
        mPresenter = Utils.checkNotNull(presenter);
    }

    @Override
    public void addList(boolean isRefresh, List<VideoBean> videoBeen) {
        if (isRefresh)
            videosAdapter.replaceData(new ArrayList<>());
//        Log.d(TAG, videoBeen.toString());
        videosAdapter.addData(videoBeen);
//        videosAdapter.setOnItemClickListener((adapter, view, position) -> {
//            VideoBean vb = videoBeen.get(position);
//            NetsOneActivity.newIntent(getActivity(),
//                    view.findViewById(R.id.img_src),
//                    view.findViewById(R.id.ltitle),
//                    vb.getPostid(),
//                    vb.getImgsrc());
//        });
    }
}
