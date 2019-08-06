package xuk.imaginary.gui.nets.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import xuk.imaginary.R
import xuk.imaginary.app.App
import xuk.imaginary.data.NetsSummary
import xuk.imaginary.databinding.FragmentNewsListBinding
import xuk.imaginary.gui.MainActivity
import xuk.imaginary.gui.nets.detail.NetsOneActivity
import xuk.imaginary.gui.web.WebViewActivity

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class NewsListFragment : Fragment() {

  private val newsListAdapter = NewsListAdapter(ArrayList())

  private lateinit var mBinding: FragmentNewsListBinding
  private lateinit var newsListViewModule: NewsListViewModule

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    mBinding = FragmentNewsListBinding.inflate(inflater, container, false)
    newsListViewModule = MainActivity.obtainNewsViewModel(activity!!)
    mBinding.viewmodule = newsListViewModule
    return mBinding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    mBinding.recyclerView.adapter = newsListAdapter
    newsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)

    mBinding.materialRefresh.setMaterialRefreshListener(object : MaterialRefreshListener() {
      override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
        newsListViewModule.getList(true)
        materialRefreshLayout.postDelayed({ materialRefreshLayout.finishRefresh() }, 1000)
      }

      override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout?) {
        super.onRefreshLoadMore(materialRefreshLayout)
        newsListViewModule.getList(false)
        materialRefreshLayout!!.postDelayed({ materialRefreshLayout.finishRefreshLoadMore() }, 1000)
      }
    })
    newsListAdapter.setOnItemClickListener { adapter, v, position ->
      val ns = adapter.data[position] as NetsSummary
      if (ns.imgextra != null && ns.photosetID != null) {
        val intent = Intent(App.context, WebViewActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        var pv = ns.photosetID
        Log.d(TAG, "ns:" + ns.toString())
        Log.d(TAG, "pv:" + pv!!)

        pv = pv.replace("|", "/")
        intent.putExtra("url", "http://news.163.com/photoview/$pv.html")
        App.context!!.startActivity(intent)
      }
      NetsOneActivity.newIntent(this.activity!!,
          v.findViewById(R.id.img_src),
          v.findViewById(R.id.title),
          ns.postid!!,
          ns.imgsrc!!)
    }
    newsListViewModule.getList(true)
  }

  companion object {
    private const val TAG = "NewsListFragment"

    val instance: NewsListFragment
      get() = NewsListFragment()
  }

}
