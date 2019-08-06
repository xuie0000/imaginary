package xuk.imaginary.gui.nets.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import xuk.imaginary.databinding.FragmentVideosBinding
import xuk.imaginary.gui.MainActivity
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class VideosFragment : Fragment() {

  private lateinit var mVideoType: String
  private val videosAdapter = VideosAdapter(ArrayList())
  private lateinit var mBinding: FragmentVideosBinding
  private lateinit var videosViewModule: VideosViewModule

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mVideoType = arguments!!.getString(VIDEO_TYPE_ID).toString()
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    mBinding = FragmentVideosBinding.inflate(inflater, container, false)
    videosViewModule = MainActivity.obtainVideosViewModel(activity!!)
    mBinding.viewModule = videosViewModule
    return mBinding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
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
