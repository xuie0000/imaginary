package com.xuie.imaginaryandroid.gui.show;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.data.GankBean;
import com.xuie.imaginaryandroid.data.source.GankRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xuie.imaginaryandroid.util.Utils.checkNotNull;

public class GankActivity extends AppCompatActivity implements GankContract.View {

    @BindView(R.id.gank_daily) ImageView gankDaily;

    private GankContract.Presenter mPresenter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);

        date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("image");
        Glide.with(this).load(imageUrl).into(gankDaily);

        new GankPresenter(GankRepository.getInstance(), this);
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (date != null)
            mPresenter.getGank(date);
    }

    @Override
    public void refresh(GankBean gb) {
        Log.d("GankActivity", gb.toString());
    }
}
