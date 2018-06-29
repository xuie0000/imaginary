package com.xuie.imaginaryandroid.gui.gank.show;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.base.BaseActivity;
import com.xuie.imaginaryandroid.data.BaseBean;
import com.xuie.imaginaryandroid.data.GankBean;
import com.xuie.imaginaryandroid.data.source.GankRepository;
import com.xuie.imaginaryandroid.databinding.ActivityGankBinding;
import com.xuie.imaginaryandroid.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuie
 */
public class GankActivity extends BaseActivity implements GankContract.View {

    private ActivityGankBinding mBinding;
    private GankContract.Presenter mPresenter = new GankPresenter(GankRepository.getInstance(), this);;
    private String date;
    private ExpandableItemAdapter adapter = new ExpandableItemAdapter(null);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        mBinding = getDataBinding();
        date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("image");
        GlideUtils.loadImage(this, imageUrl, mBinding.gankDaily);
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    protected View[] setImmersiveView() {
        return new View[0];
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (date != null) {
            mPresenter.getGank(date);
        }
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
