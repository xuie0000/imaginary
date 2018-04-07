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

/**
 * A simple [Fragment] subclass.
 */
class VideoMainFragment : Fragment() {

    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_main, container, false)

        with(view) {
            tabs = findViewById(R.id.tabs)
            viewPager = findViewById(R.id.view_pager)
            fab = findViewById(R.id.fab)
        }

        viewPager.adapter = MyPagerAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewPager)
        return view
    }

    private inner class MyPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        internal var channelName = resources.getStringArray(R.array.video_channel_name)
        internal var channelId = resources.getStringArray(R.array.video_channel_id)

        override fun getCount(): Int {
            return channelId.size
        }

        override fun getItem(position: Int): Fragment {
            return VideosFragment.getInstance(channelName[position], channelId[position])
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return channelName[position].toString()
        }
    }

    companion object {

        val instance: VideoMainFragment
            get() = VideoMainFragment()
        const val TAG = "VideoMainFragment"
    }
}
