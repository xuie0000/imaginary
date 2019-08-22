package xuk.imaginary.gui.gank.meizhi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_mei_zhi.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import xuk.imaginary.R

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
@ObsoleteCoroutinesApi
class MeiZhiFragment : Fragment() {

  private lateinit var meiZhiViewModule: MeiZhiViewModule

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_mei_zhi, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Log.d(TAG, "onViewCreated")
    meiZhiViewModule = ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        .create(MeiZhiViewModule::class.java)

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

    val meiZhiAdapter = MeiZhiAdapter()

    recyclerView.apply {
      adapter = meiZhiAdapter
      layoutManager = flexLayoutManager
    }
    meiZhiViewModule.items.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
      it?.let {
        meiZhiAdapter.submitList(it)
      }
    })

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
    private const val TAG = "MeiZhiFragment"
  }

}
