package com.xuie.imaginaryandroid.gui.show;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xuie.imaginaryandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeizhiActivity extends AppCompatActivity {

    @BindView(R.id.gank_daily) ImageView gankDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);
        ButterKnife.bind(this);

        String date = getIntent().getStringExtra("date");
        String imageUrl = getIntent().getStringExtra("image");
        Glide.with(this).load(imageUrl).into(gankDaily);
    }


}
