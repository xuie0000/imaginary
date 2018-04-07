package com.xuie.imaginaryandroid.gui.nets.video

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.data.VideoBean
import com.xuie.imaginaryandroid.data.source.NETSRepository
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
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

        presenter.subscribe()
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

   override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
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

        fun getInstance(typeName: Array<String>, typeId: Array<String>): VideosFragment {
            val fragment = VideosFragment()
            val bundle = Bundle()
            bundle.putString(VIDEO_TYPE_NAME, typeName.toString())
            bundle.putString(VIDEO_TYPE_ID, typeId.toString())
            fragment.arguments = bundle
            return fragment
        }
    }
}
