package com.xuie.imaginary.gui.nets.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.xuie.imaginary.databinding.FragmentVideosBinding
import com.xuie.imaginary.di.viewModel
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class VideosFragment : Fragment(), KodeinAware {

  override val kodein by kodein()

  private lateinit var mVideoType: String
  private val videosAdapter = VideosAdapter(ArrayList())
  private lateinit var mBinding: FragmentVideosBinding
  private val videosViewModule: VideosViewModule by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    mBinding = FragmentVideosBinding.inflate(inflater, container, false)
    mBinding.viewModule = videosViewModule
    return mBinding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mVideoType = arguments!!.getString(VIDEO_TYPE_ID)
    mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
    mBinding.recyclerView.adapter = videosAdapter

    videosAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)

    videosViewModule.getList(mVideoType, true)

    mBinding.materialRefresh.setMaterialRefreshListener(object : MaterialRefreshListener() {
      override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
        videosViewModule.getList(mVideoType, true)
        materialRefreshLayout.postDelayed({ materialRefreshLayout.finishRefresh() }, 1000)
      }

      override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout?) {
        super.onRefreshLoadMore(materialRefreshLayout)
        videosViewModule.getList(mVideoType, false)
        materialRefreshLayout!!.postDelayed({ materialRefreshLayout.finishRefreshLoadMore() }, 1000)
      }
    })
  }

  override fun onPause() {
    super.onPause()
    JCVideoPlayer.releaseAllVideos()
  }

  companion object {
    private const val TAG = "VideosFragment"
    const val VIDEO_TYPE_ID = "type"
    private const val VIDEO_TYPE_NAME = "name"

    fun getInstance(typeName: String, typeId: String): VideosFragment {
      val fragment = VideosFragment()
      val bundle = Bundle()
      bundle.putString(VIDEO_TYPE_NAME, typeName)
      bundle.putString(VIDEO_TYPE_ID, typeId)
      fragment.arguments = bundle
      return fragment
    }
  }
}
