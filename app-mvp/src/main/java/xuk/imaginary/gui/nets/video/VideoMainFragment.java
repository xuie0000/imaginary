package xuk.imaginary.gui.nets.video;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

import xuk.imaginary.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class VideoMainFragment extends Fragment {

    public static VideoMainFragment getInstance() {
        return new VideoMainFragment();
    }

    private TabLayout tabs;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_main, container, false);
        tabs = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.view_pager);
        fab = view.findViewById(R.id.fab);

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

}
