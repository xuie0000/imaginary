package xuk.imaginary.gui.nets.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import xuk.imaginary.R;
import xuk.imaginary.databinding.FragmentVideoMainBinding;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author xuie
 */
public class VideoMainFragment extends Fragment {

    public static VideoMainFragment getInstance() {
        return new VideoMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentVideoMainBinding binding = FragmentVideoMainBinding.inflate(inflater, container, false);
        binding.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        binding.tabs.setupWithViewPager(binding.viewPager);
        return binding.getRoot();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<String> channelName = Arrays.asList(getResources().getStringArray(R.array.video_channel_name));
        List<String> channelId = Arrays.asList(getResources().getStringArray(R.array.video_channel_id));

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
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
