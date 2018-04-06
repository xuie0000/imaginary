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

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer

import com.xuie.imaginaryandroid.util.Utils.checkNotNull

/**
 * A simple [Fragment] subclass.
 */
class VideosFragment : Fragment(), VideosContract.View {

    @BindView(R.id.recycler_view)
    internal var recycleView: RecyclerView? = null
    @BindView(R.id.material_refresh)
    internal var materialRefresh: MaterialRefreshLayout? = null
    internal var unbinder: Unbinder? = null

    private var mVideoType: String? = null
    private var mPresenter: VideosContract.Presenter? = null
    private val videosAdapter = VideosAdapter(null)

    @Override
    fun onCreate(@Nullable savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        mVideoType = getArguments().getString(VIDEO_TYPE_ID)

        VideosPresenter(NETSRepository.getInstance(), this)
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View {
        val view = inflater.inflate(R.layout.fragment_videos, container, false)
        unbinder = ButterKnife.bind(this, view)

        recycleView!!.setLayoutManager(LinearLayoutManager(getContext()))
        recycleView!!.setAdapter(videosAdapter)

        videosAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)

        mPresenter!!.subscribe()
        mPresenter!!.getList(mVideoType, true)


        materialRefresh!!.setMaterialRefreshListener(object : MaterialRefreshListener() {
            @Override
            fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
                mPresenter!!.getList(mVideoType, true)
                materialRefreshLayout.postDelayed(???({ materialRefreshLayout.finishRefresh() }), 1000)
            }

            @Override
            fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout)
                mPresenter!!.getList(mVideoType, false)
                materialRefreshLayout.postDelayed(???({ materialRefreshLayout.finishRefreshLoadMore() }), 1000)
            }
        })
        return view
    }

    @Override
    fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }

    @Override
    fun onDestroyView() {
        super.onDestroyView()
        mPresenter!!.unsubscribe()
        unbinder!!.unbind()
    }

    @Override
    fun setPresenter(presenter: VideosContract.Presenter) {
        mPresenter = checkNotNull(presenter)
    }

    @Override
    fun addList(isRefresh: Boolean, videoBeen: List<VideoBean>) {
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

        val VIDEO_TYPE_ID = "type"
        val VIDEO_TYPE_NAME = "name"

        fun getInstance(typeName: String, typeId: String): VideosFragment {
            val fragment = VideosFragment()
            val bundle = Bundle()
            bundle.putString(VIDEO_TYPE_NAME, typeName)
            bundle.putString(VIDEO_TYPE_ID, typeId)
            fragment.setArguments(bundle)
            return fragment
        }
    }
}
