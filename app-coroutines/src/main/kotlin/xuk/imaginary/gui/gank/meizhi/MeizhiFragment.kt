package xuk.imaginary.gui.gank.meizhi

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_meizhi.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import xuk.imaginary.R
import xuk.imaginary.data.GankIo
import xuk.imaginary.gui.gank.show.GankActivity
import xuk.imaginary.util.DateUtils
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
@ObsoleteCoroutinesApi
class MeizhiFragment : Fragment() {

  private lateinit var meiZhiViewModule: MeiZhiViewModule

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_meizhi, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Log.d(TAG, "onViewCreated")
    meiZhiViewModule = ViewModelProviders.of(this).get(MeiZhiViewModule::class.java)

    val flexLayoutManager = FlexboxLayoutManager(context).apply {
      // http://shinelw.com/2017/04/13/flexbox-layout-analysis/
      // 水平
      flexDirection = FlexDirection.ROW
      // 自动换行
      flexWrap = FlexWrap.WRAP
//      justifyContent = JustifyContent.FLEX_END
      // 主轴起点对齐
      alignItems = AlignItems.FLEX_START
    }

    val meiZhiAdapter = MeiZhiAdapter(ArrayList()).apply {
      openLoadAnimation(BaseQuickAdapter.ALPHAIN)
      isFirstOnly(true)
    }

    recyclerView.apply {
      adapter = meiZhiAdapter
      layoutManager = flexLayoutManager
    }

    meiZhiViewModule.items.observe(this, androidx.lifecycle.Observer {
      it?.apply {
        meiZhiAdapter.replaceData(it)
      }
    })

    meiZhiAdapter.setOnItemClickListener { adapter, v, position ->
      val fl = adapter.data[position] as GankIo.BaseBean
      Log.d(TAG, "position:$position, data:$fl")
      val dateString = DateUtils.getDate(fl.publishedAt)
      val intent = Intent(activity, GankActivity::class.java)
      intent.putExtra("date", dateString)
      intent.putExtra("image", fl.url)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, v, "shareObject")
      ActivityCompat.startActivity(activity!!, intent, options.toBundle())
    }

    materialRefresh.setMaterialRefreshListener(object : MaterialRefreshListener() {
      override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
        meiZhiViewModule.refresh(true)
        materialRefreshLayout.postDelayed({ materialRefreshLayout.finishRefresh() }, 1000)
      }

      override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout?) {
        super.onRefreshLoadMore(materialRefreshLayout)
        meiZhiViewModule.refresh(false)
        materialRefreshLayout?.postDelayed({ materialRefreshLayout.finishRefreshLoadMore() }, 1000)
      }
    })

    meiZhiViewModule.start()
  }

  companion object {
    private const val TAG = "MeizhiFragment"
  }

}
