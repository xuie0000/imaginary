package com.xuie.imaginaryandroid.gui.nets.news

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.xuie.imaginaryandroid.R
import com.xuie.imaginaryandroid.app.App
import com.xuie.imaginaryandroid.data.NetsSummary
import com.xuie.imaginaryandroid.data.source.NETSRepository
import com.xuie.imaginaryandroid.gui.nets.detail.NetsOneActivity
import com.xuie.imaginaryandroid.gui.web.WebViewActivity

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

import com.xuie.imaginaryandroid.util.Utils.checkNotNull

/**
 * A simple [Fragment] subclass.
 */
class NewsListFragment : Fragment(), NewsListContract.View {

    @BindView(R.id.recycler_view)
    internal var recycleView: RecyclerView? = null
    @BindView(R.id.material_refresh)
    internal var materialRefresh: MaterialRefreshLayout? = null
    internal var unbinder: Unbinder? = null

    private var mPresenter: NewsListContract.Presenter? = null
    private val newsListAdapter = NewsListAdapter(null)


    @Override
    fun onCreate(@Nullable savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        NewsListPresenter(NETSRepository.getInstance(), this)
    }

    @Override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        unbinder = ButterKnife.bind(this, view)

        recycleView!!.setLayoutManager(StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL))
        recycleView!!.setAdapter(newsListAdapter)
        newsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        mPresenter!!.subscribe()

        materialRefresh!!.setMaterialRefreshListener(object : MaterialRefreshListener() {
            @Override
            fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
                mPresenter!!.getList(true)
                materialRefreshLayout.postDelayed(???({ materialRefreshLayout.finishRefresh() }), 1000)
            }

            @Override
            fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout)
                mPresenter!!.getList(false)
                materialRefreshLayout.postDelayed(???({ materialRefreshLayout.finishRefreshLoadMore() }), 1000)
            }
        })

        return view
    }

    @Override
    fun onDestroyView() {
        super.onDestroyView()
        mPresenter!!.unsubscribe()
        unbinder!!.unbind()
    }

    @Override
    fun setPresenter(presenter: NewsListContract.Presenter) {
        mPresenter = checkNotNull(presenter)
    }

    @Override
    fun addList(isRefresh: Boolean, netsSummaries: List<NetsSummary>) {
        if (isRefresh)
            newsListAdapter.replaceData(ArrayList())
        //        Log.d(TAG, netsSummaries.toString());
        newsListAdapter.addData(netsSummaries)
        newsListAdapter.setOnItemClickListener({ adapter, view, position ->
            val ns = adapter.getData().get(position) as NetsSummary
            if (ns.getImgextra() != null && ns.getPhotosetID() != null) {
                val intent = Intent(App.getContext(), WebViewActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                var pv = ns.getPhotosetID()
                Log.d(TAG, "ns:" + ns.toString())
                Log.d(TAG, "pv:$pv")

                pv = pv.replace("|", "/")
                intent.putExtra("url", "http://news.163.com/photoview/$pv.html")
                App.getContext().startActivity(intent)
                return@newsListAdapter.setOnItemClickListener
            }
            NetsOneActivity.newIntent(getActivity(),
                    view.findViewById(R.id.img_src),
                    view.findViewById(R.id.ltitle),
                    ns.getPostid(),
                    ns.getImgsrc())
        })
    }

    companion object {
        private val TAG = "NewsListFragment"

        val instance: NewsListFragment
            get() = NewsListFragment()
    }
}
