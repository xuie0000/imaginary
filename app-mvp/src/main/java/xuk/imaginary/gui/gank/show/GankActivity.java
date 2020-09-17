package xuk.imaginary.gui.gank.show;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import xuk.imaginary.R;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.data.GankBean;
import xuk.imaginary.data.source.GankRepository;
import xuk.imaginary.gui.BaseActivity;
import xuk.imaginary.util.GlideUtils;
import xuk.imaginary.util.Utils;

/**
 * @author xuie
 */
@ParallaxBack
public class GankActivity extends BaseActivity implements GankContract.View {

    private RecyclerView recyclerView;
    private PhotoView gankDaily;

    private GankContract.Presenter mPresenter;
    private String date;
    private ExpandableItemAdapter adapter = new ExpandableItemAdapter(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        recyclerView = findViewById(R.id.recycler_view);
        gankDaily = findViewById(R.id.gank_daily);

        date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("image");
        GlideUtils.loadImageMeizhiDetail(this, imageUrl, gankDaily);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        new GankPresenter(GankRepository.getInstance(), this);
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = Utils.checkNotNull(presenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (date != null) {
            mPresenter.getGank(date);
        }
    }

    @Override
    public void refresh(GankBean gb) {
//        Log.d("GankActivity", gb.toString())
        List<MultiItemEntity> entities = generateData(gb);
        adapter.replaceData(new ArrayList<>());
        adapter.addData(entities);
        adapter.expandAll();
    }

    private List<MultiItemEntity> generateData(GankBean gb) {
        List<MultiItemEntity> entities = new ArrayList<>();
        for (String s : gb.getCategory()) {
            Level0Item lv0 = new Level0Item();
            lv0.setType(s);
            List<BaseBean> bbs = gb.getResults().getAndroid();
            switch (s) {
                case "Android":
                    bbs = gb.getResults().getAndroid();
                    break;
                case "瞎推荐":
                    bbs = gb.getResults().get瞎推荐();
                    break;
                case "iOS":
                    bbs = gb.getResults().getiOS();
                    break;
                case "休息视频":
                    bbs = gb.getResults().get休息视频();
                    break;
                case "福利":
//                bbs = gb.getResults().get福利()
                    continue;
                case "前端":
                    bbs = gb.getResults().get前端();
                    break;
                case "拓展资源":
                    bbs = gb.getResults().get拓展资源();
                    break;
                default:
                    break;
            }
            for (BaseBean ab : bbs) {
                Level1Item lv1 = new Level1Item();
                lv1.setArticleName(ab.getDesc());
                lv1.setArticleUrl(ab.getUrl());
                lv1.setAuthor(ab.getWho());
                if (ab.getImages() != null && ab.getImages().size() > 0) {
                    lv1.setImageUrl(ab.getImages().get(0));
                }
                lv0.addSubItem(lv1);
            }
            entities.add(lv0);
        }
        return entities;
    }
}
