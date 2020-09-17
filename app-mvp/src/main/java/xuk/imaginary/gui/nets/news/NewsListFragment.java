package xuk.imaginary.gui.nets.news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import xuk.imaginary.R;
import xuk.imaginary.app.App;
import xuk.imaginary.data.NetsSummary;
import xuk.imaginary.data.source.NetsRepository;
import xuk.imaginary.gui.nets.detail.NetsOneActivity;
import xuk.imaginary.gui.web.WebViewActivity;
import xuk.imaginary.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class NewsListFragment extends Fragment implements NewsListContract.View {
    private static final String TAG = "NewsListFragment";

    public static NewsListFragment getInstance() {
        return new NewsListFragment();
    }

    private RecyclerView recycleView;
    private MaterialRefreshLayout materialRefresh;

    private NewsListContract.Presenter mPresenter;
    private NewsListAdapter newsListAdapter = new NewsListAdapter(null);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NewsListPresenter(NetsRepository.getInstance(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        recycleView = view.findViewById(R.id.recycler_view);
        materialRefresh = view.findViewById(R.id.material_refresh);

        recycleView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
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
    }

    @Override
    public void setPresenter(NewsListContract.Presenter presenter) {
        mPresenter = Utils.checkNotNull(presenter);
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
