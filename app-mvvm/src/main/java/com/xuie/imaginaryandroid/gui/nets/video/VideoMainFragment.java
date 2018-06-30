package com.xuie.imaginaryandroid.gui.nets.video;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xuie.imaginaryandroid.R;
import com.xuie.imaginaryandroid.base.BaseFragment;
import com.xuie.imaginaryandroid.databinding.FragmentVideoMainBinding;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class VideoMainFragment extends BaseFragment {

    public static VideoMainFragment getInstance() {
        return new VideoMainFragment();
    }

    private FragmentVideoMainBinding mBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_main;
    }

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        mBinding = getDataBinding();

        mBinding.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mBinding.tabs.setupWithViewPager(mBinding.viewPager);
    }

    @Override
    protected void lazyInitData() {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<String> channelName = Arrays.asList(getResources().getStringArray(R.array.video_channel_name));
        List<String> channelId = Arrays.asList(getResources().getStringArray(R.array.video_channel_id));

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return VideosFragment.getInstance(channelName.get(position), channelId.get(position));
        }

        @Override
        public int getCount() {
            return channelId.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return channelName.get(position);
        }
    }
}
