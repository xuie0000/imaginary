package xuk.imaginary.gui.nets.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import xuk.imaginary.R
import xuk.imaginary.data.VideoBean
import xuk.imaginary.data.source.NETSRepository
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class VideosFragment : Fragment(), VideosContract.View {
  override lateinit var presenter: VideosContract.Presenter

  private lateinit var recycleView: RecyclerView
  private lateinit var materialRefresh: MaterialRefreshLayout

  private var mVideoType: String? = "name"
  private val videosAdapter = VideosAdapter(null)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mVideoType = arguments!!.getString(VIDEO_TYPE_ID)

    VideosPresenter(NETSRepository.instance, this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_videos, container, false)

    with(view) {
      recycleView = findViewById(R.id.recycler_view)
      materialRefresh = findViewById(R.id.material_refresh)
    }

    recycleView.layoutManager = LinearLayoutManager(context)
    recycleView.adapter = videosAdapter

    videosAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)

    presenter.start()
    presenter.getList(mVideoType!!, true)


    materialRefresh.setMaterialRefreshListener(object : MaterialRefreshListener() {
      override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
        presenter.getList(mVideoType!!, true)
        materialRefreshLayout.postDelayed({ materialRefreshLayout.finishRefresh() }, 1000)
      }

      override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout) {
        super.onRefreshLoadMore(materialRefreshLayout)
        presenter.getList(mVideoType!!, false)
        materialRefreshLayout.postDelayed({ materialRefreshLayout.finishRefreshLoadMore() }, 1000)
      }
    })
    return view
  }

  override fun onPause() {
    super.onPause()
    JCVideoPlayer.releaseAllVideos()
  }

  override fun addList(isRefresh: Boolean, videoBeen: List<VideoBean>) {
    if (isRefresh)
      videosAdapter.replaceData(ArrayList())
    //        Log.d(TAG, videoBeen.toString());
    videosAdapter.addData(videoBeen)
    //        videosAdapter.setOnItemClickListener((adapter, view, position) -> {
    //            VideoBean vb = videoBeen.get(position);
    //            NetsOneActivity.newIntent(getActivity(),
    //                    view.findViewById(R.id.img_src),
    //                    view.findViewById(R.id.ltitle),
    //                    vb.getPostid(),
    //                    vb.getImgsrc());
    //        });
  }

  companion object {

    const val VIDEO_TYPE_ID = "type"
    private const val VIDEO_TYPE_NAME = "name"

    fun getInstance(typeName: String, typeId: String): VideosFragment {
      val fragment = VideosFragment()
      val bundle = Bundle()
      bundle.putString(VIDEO_TYPE_NAME, typeName.toString())
      bundle.putString(VIDEO_TYPE_ID, typeId.toString())
      fragment.arguments = bundle
      return fragment
    }
  }
}
