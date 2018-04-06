package com.xuie.imaginaryandroid.gui.nets.video


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.xuie.imaginaryandroid.R

import java.util.Arrays

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * A simple [Fragment] subclass.
 */
class VideoMainFragment : Fragment() {

    @BindView(R.id.tabs)
    internal var tabs: TabLayout? = null
    @BindView(R.id.view_pager)
    internal var viewPager: ViewPager? = null
    @BindView(R.id.fab)
    internal var fab: FloatingActionButton? = null
    internal var unbinder: Unbinder? = null

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View {
        val view = inflater.inflate(R.layout.fragment_video_main, container, false)
        unbinder = ButterKnife.bind(this, view)

        viewPager!!.setAdapter(MyPagerAdapter(getChildFragmentManager()))
        tabs!!.setupWithViewPager(viewPager)
        return view
    }

    private inner class MyPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        internal var channelName = Arrays.asList(getResources().getStringArray(R.array.video_channel_name))
        internal var channelId = Arrays.asList(getResources().getStringArray(R.array.video_channel_id))

        val count: Int
            @Override
            get() = channelId.size()

        @Override
        fun getItem(position: Int): Fragment {
            return VideosFragment.getInstance(channelName.get(position), channelId.get(position))
        }

        @Override
        fun getPageTitle(position: Int): CharSequence {
            return channelName.get(position)
        }
    }

    @Override
    fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    companion object {

        val instance: VideoMainFragment
            get() = VideoMainFragment()
    }
}
