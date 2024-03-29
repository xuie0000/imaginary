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
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import xuk.imaginary.data.BaseBean
import xuk.imaginary.databinding.FragmentMeizhiBinding
import xuk.imaginary.di.viewModel
import xuk.imaginary.gui.gank.show.GankActivity
import xuk.imaginary.util.DateUtils
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class MeizhiFragment : Fragment(), KodeinAware {

  override val kodein by kodein()

  private lateinit var binding: FragmentMeizhiBinding
  private val meiZhiViewModule: MeiZhiViewModule by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    Log.d(TAG, "onCreateView")
    binding = FragmentMeizhiBinding.inflate(inflater, container, false)
    binding.viewmodule = meiZhiViewModule
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Log.d(TAG, "onViewCreated")
    val layoutManager = FlexboxLayoutManager(context).apply {
      // http://shinelw.com/2017/04/13/flexbox-layout-analysis/
      // 水平
      flexDirection = FlexDirection.ROW
      // 自动换行
      flexWrap = FlexWrap.WRAP
//      justifyContent = JustifyContent.FLEX_END
      // 主轴起点对齐
      alignItems = AlignItems.FLEX_START
    }
    binding.recyclerView.layoutManager = layoutManager

    val meiZhiAdapter = MeiZhiAdapter(ArrayList()).apply {
      openLoadAnimation(BaseQuickAdapter.ALPHAIN)
      isFirstOnly(true)
    }
    binding.recyclerView.adapter = meiZhiAdapter

    meiZhiAdapter.setOnItemClickListener { adapter, v, position ->
      val fl = adapter.data[position] as BaseBean
      Log.d(TAG, "position:$position, data:$fl")
      val dateString = DateUtils.getDate(fl.publishedAt)
      val intent = Intent(activity, GankActivity::class.java)
      intent.putExtra("date", dateString)
      intent.putExtra("image", fl.url)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, v, "shareObject")
      ActivityCompat.startActivity(activity!!, intent, options.toBundle())
    }

    binding.materialRefresh.setMaterialRefreshListener(object : MaterialRefreshListener() {
      override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
        meiZhiViewModule.getList(true)
        materialRefreshLayout.postDelayed({ materialRefreshLayout.finishRefresh() }, 1000)
      }

      override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout?) {
        super.onRefreshLoadMore(materialRefreshLayout)
        meiZhiViewModule.getList(false)
        materialRefreshLayout!!.postDelayed({ materialRefreshLayout.finishRefreshLoadMore() }, 1000)
      }
    })
    meiZhiViewModule.start()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    meiZhiViewModule.end()
  }

  companion object {
    private const val TAG = "MeizhiFragment"

    val instance: MeizhiFragment
      get() = MeizhiFragment()
  }

}
