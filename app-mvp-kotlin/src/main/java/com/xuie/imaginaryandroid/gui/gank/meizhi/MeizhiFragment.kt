package com.xuie.imaginaryandroid.gui.gank.meizhi


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.Fragment
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
import com.xuie.imaginaryandroid.data.BaseBean
import com.xuie.imaginaryandroid.data.source.GankRepository
import com.xuie.imaginaryandroid.gui.gank.show.GankActivity
import com.xuie.imaginaryandroid.util.DateUtils
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MeizhiFragment : Fragment(), MeizhiContract.View {

    override lateinit var presenter: MeizhiContract.Presenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var materialRefresh: MaterialRefreshLayout

    private val meiZhiAdapter = MeiZhiAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MeizhiPresenter(GankRepository.instance, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_meizhi, container, false)
        Log.d(TAG, "onCreateView")

        with(v) {
            recyclerView = findViewById(R.id.recycler_view)
            materialRefresh = findViewById(R.id.material_refresh)
        }

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = meiZhiAdapter
        meiZhiAdapter.setOnItemClickListener({ adapter, view, position ->
            Log.d(TAG, "position:$position")
            val fl = adapter.data[position] as BaseBean
            Log.d(TAG, fl.toString())
            val dateString = DateUtils.getDate(fl.publishedAt!!)
            val intent = Intent(activity, GankActivity::class.java)
            intent.putExtra("date", dateString)
            intent.putExtra("image", fl.url)
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, "shareObject")
            ActivityCompat.startActivity(activity!!, intent, options.toBundle())
        })
        meiZhiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        meiZhiAdapter.isFirstOnly(true)

        materialRefresh.setMaterialRefreshListener(object : MaterialRefreshListener() {
            override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
                presenter.getList(true)
                materialRefreshLayout.postDelayed({ materialRefreshLayout.finishRefresh() }, 1000)
            }

            override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout)
                presenter.getList(false)
                materialRefreshLayout.postDelayed({ materialRefreshLayout.finishRefreshLoadMore() }, 1000)
            }
        })

        presenter.start()
        return v
    }

    override fun addList(isRefresh: Boolean, meiZhis: List<BaseBean>) {
        if (isRefresh)
            meiZhiAdapter.replaceData(ArrayList())
//        Log.d(TAG, meiZhis.toString());
        meiZhiAdapter.addData(meiZhis)
    }

    companion object {
        private const val TAG = "MeizhiFragment"

        val instance: MeizhiFragment
            get() = MeizhiFragment()
    }
}
