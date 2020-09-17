package xuk.imaginary.gankdate;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import xuk.imaginary.R;
import xuk.imaginary.data.BaseBean;
import xuk.imaginary.data.GankBean;
import xuk.imaginary.util.GlideUtils;

/**
 * @author xuie
 */
public class GankDayActivity extends DaggerAppCompatActivity implements GankContract.View {
    private static final String TAG = "GankDayActivity";
    private PhotoView gankDaily;
    private RecyclerView recyclerView;

    @Inject GankPresenter mPresenter;
    private String date;
    private ExpandableItemAdapter adapter = new ExpandableItemAdapter(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_day);
        gankDaily = findViewById(R.id.gank_daily);
        recyclerView = findViewById(R.id.recycler_view);

        date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("image");
        GlideUtils.loadImageMeizhiDetail(this, imageUrl, gankDaily);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }


    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
        }
        mPresenter.takeView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (date != null)
            mPresenter.getGank(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void refresh(GankBean gb) {
        Log.d(TAG, gb.toString());
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
                if (ab.getImages() != null && ab.getImages().size() > 0)
                    lv1.setImageUrl(ab.getImages().get(0));
                lv0.addSubItem(lv1);
            }
            entities.add(lv0);
        }
        return entities;
    }
}
