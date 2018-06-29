package com.xuie.imaginaryandroid.gui.gank.show;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.github.chrisbanes.photoview.PhotoView;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.BaseBean;
import com.xuie.imaginaryandroid.data.GankBean;
import com.xuie.imaginaryandroid.data.source.GankRepository;
import com.xuie.imaginaryandroid.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

/**
 * @author xuie
 */
@ParallaxBack
public class GankActivity extends AppCompatActivity implements GankContract.View {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.gank_daily) PhotoView gankDaily;

    private GankContract.Presenter mPresenter;
    private String date;
    private ExpandableItemAdapter adapter = new ExpandableItemAdapter(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);

        date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("image");
        GlideUtils.loadImage(this, imageUrl, gankDaily);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        new GankPresenter(GankRepository.getInstance(), this);
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (date != null)
            mPresenter.getGank(date);
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
    }

    @Override
    public void refresh(GankBean gb) {
//        Log.d("GankActivity", gb.toString());
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
            if (s.equals("Android")) {
                bbs = gb.getResults().getAndroid();
            } else if (s.equals("瞎推荐")) {
                bbs = gb.getResults().get瞎推荐();
            } else if (s.equals("iOS")) {
                bbs = gb.getResults().getiOS();
            } else if (s.equals("休息视频")) {
                bbs = gb.getResults().get休息视频();
            } else if (s.equals("福利")) {
//                bbs = gb.getResults().get福利();
                continue;
            } else if (s.equals("前端")) {
                bbs = gb.getResults().get前端();
            } else if (s.equals("拓展资源")) {
                bbs = gb.getResults().get拓展资源();
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
