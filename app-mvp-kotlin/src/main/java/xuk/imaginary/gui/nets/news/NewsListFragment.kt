package xuk.imaginary.gui.nets.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import xuk.imaginary.R
import xuk.imaginary.app.App
import xuk.imaginary.data.NetsSummary
import xuk.imaginary.data.source.NETSRepository
import xuk.imaginary.gui.nets.detail.NetsOneActivity
import xuk.imaginary.gui.web.WebViewActivity
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class NewsListFragment : Fragment(), NewsListContract.View {
  override lateinit var presenter: NewsListContract.Presenter

  //    @BindView(R.id.recycler_view)
  private lateinit var recycleView: RecyclerView

  //    @BindView(R.id.material_refresh)
  private lateinit var materialRefresh: MaterialRefreshLayout

  private val newsListAdapter = NewsListAdapter(null)


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    NewsListPresenter(NETSRepository.instance, this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_news_list, container, false)

    with(view) {
      recycleView = findViewById(R.id.recycler_view)
      materialRefresh = findViewById(R.id.material_refresh)
    }

    recycleView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
    recycleView.adapter = newsListAdapter
    newsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
    presenter.start()

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

    return view
  }

  override fun addList(isRefresh: Boolean, netsSummaries: List<NetsSummary>) {
    if (isRefresh)
      newsListAdapter.replaceData(ArrayList())
    //        Log.d(TAG, netsSummaries.toString());
    newsListAdapter.addData(netsSummaries)
    newsListAdapter.setOnItemClickListener({ adapter, view, position ->
      val ns = adapter.data[position] as NetsSummary
      if (ns.imgextra != null && ns.photosetID != null) {
        val intent = Intent(App.context, WebViewActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        var pv = ns.photosetID
        Log.d(TAG, "ns:" + ns.toString())
        Log.d(TAG, "pv:$pv")

        pv = pv!!.replace("|", "/")
        intent.putExtra("url", "http://news.163.com/photoview/$pv.html")
        App.context.startActivity(intent)
        return@setOnItemClickListener
      }
      NetsOneActivity.newIntent(activity!!,
          view.findViewById(R.id.img_src),
          view.findViewById(R.id.ltitle),
          ns.postid!!,
          ns.imgsrc!!)
    })
  }

  companion object {
    private const val TAG = "NewsListFragment"

    val instance: NewsListFragment
      get() = NewsListFragment()
  }
}
