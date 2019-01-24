package com.xuie.imaginary.gui.nets.video;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xuie.imaginary.R;
import com.xuie.imaginary.base.BaseFragment;
import com.xuie.imaginary.databinding.FragmentVideoMainBinding;

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
