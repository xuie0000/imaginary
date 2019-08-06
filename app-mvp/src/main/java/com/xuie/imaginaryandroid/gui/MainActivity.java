package xuk.imaginary.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import xuk.imaginary.R;
import xuk.imaginary.gui.gank.meizhi.MeizhiFragment;
import xuk.imaginary.gui.nets.news.NewsListFragment;
import xuk.imaginary.gui.nets.video.VideoMainFragment;
import xuk.imaginary.gui.settings.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * @author xuie
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.navigation) BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_video:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_gank:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_settings:
                default:
                    viewPager.setCurrentItem(3);
                    return true;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate");

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean isUser = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (isUser) {
                    int itemId = navigation.getMenu().getItem(position).getItemId();
                    navigation.setSelectedItemId(itemId);
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
        navigation.setSelectedItemId(navigation.getMenu().getItem(1).getItemId());
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewsListFragment.getInstance();
                case 1:
                    return VideoMainFragment.getInstance();
                case 2:
                    return MeizhiFragment.getInstance();
                case 3:
                default:
                    return SettingsFragment.getInstance();
            }
        }

        @Override
        public int getCount() {
            return 4;
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
