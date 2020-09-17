package xuk.imaginary.gui.nets.video;


import android.os.Bundle;
import androidx.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentPagerAdapter;
import androidx.core.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xuk.imaginary.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * @author xuie
 */
public class VideoMainFragment extends Fragment {

    public static VideoMainFragment getInstance() {
        return new VideoMainFragment();
    }

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.fab) FloatingActionButton fab;
    Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tabs.setupWithViewPager(viewPager);
        return view;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
