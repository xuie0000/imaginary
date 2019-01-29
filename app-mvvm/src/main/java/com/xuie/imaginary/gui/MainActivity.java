package com.xuie.imaginary.gui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuie.imaginary.R;
import com.xuie.imaginary.ViewModelFactory;
import com.xuie.imaginary.databinding.ActivityMainBinding;
import com.xuie.imaginary.gui.gank.meizhi.MeiZhiViewModule;
import com.xuie.imaginary.gui.gank.meizhi.MeizhiFragment;
import com.xuie.imaginary.gui.nets.news.NewsListFragment;
import com.xuie.imaginary.gui.nets.news.NewsListViewModule;
import com.xuie.imaginary.gui.nets.video.VideoMainFragment;
import com.xuie.imaginary.gui.nets.video.VideosViewModule;

import org.jetbrains.annotations.NotNull;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * @author xuie
 */
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mBinding.viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_video:
                    mBinding.viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_gank:
                    mBinding.viewPager.setCurrentItem(2);
                    return true;
//                case R.id.navigation_settings:
//                    mBinding.viewPager.setCurrentItem(3)
//                    return true;
                default:
                    return true;
            }
        }

    };

    public static MeiZhiViewModule obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MeiZhiViewModule.class);
    }

    public static NewsListViewModule obtainNewsViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(NewsListViewModule.class);
    }

    public static VideosViewModule obtainVideosViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(VideosViewModule.class);
    }

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBinding.viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean isUser = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (isUser) {
                    int itemId = mBinding.navigation.getMenu().getItem(position).getItemId();
                    mBinding.navigation.setSelectedItemId(itemId);
                    isUser = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isUser = true;
                }
            }
        });
        mBinding.navigation.setSelectedItemId(mBinding.navigation.getMenu().getItem(1).getItemId());
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewsListFragment.getInstance();
                case 1:
                    return VideoMainFragment.getInstance();
                case 2:
                default:
                    return MeizhiFragment.getInstance();
//                case 3:
//                    return SettingsFragment.getInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
