package xuk.imaginary.gui.nets.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import xuk.imaginary.R
import xuk.imaginary.databinding.FragmentVideoMainBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class VideoMainFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentVideoMainBinding.inflate(inflater, container, false)
    binding.viewPager.adapter = MyPagerAdapter(childFragmentManager)
    binding.tabs.setupWithViewPager(binding.viewPager)
    return binding.root
  }

  private inner class MyPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    internal var channelName = Arrays.asList(*resources.getStringArray(R.array.video_channel_name))
    internal var channelId = Arrays.asList(*resources.getStringArray(R.array.video_channel_id))

    override fun getItem(position: Int): Fragment {
      return VideosFragment.getInstance(channelName[position], channelId[position])
    }

    override fun getCount(): Int {
      return channelId.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return channelName[position]
    }
  }

  companion object {

    val instance: VideoMainFragment
      get() = VideoMainFragment()
  }
}
