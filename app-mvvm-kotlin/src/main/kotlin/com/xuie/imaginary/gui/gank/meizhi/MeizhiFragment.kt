package com.xuie.imaginary.gui.gank.meizhi

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.chad.library.adapter.base.BaseQuickAdapter
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.xuie.imaginary.data.BaseBean
import com.xuie.imaginary.databinding.FragmentMeizhiBinding
import com.xuie.imaginary.gui.MainActivity
import com.xuie.imaginary.gui.gank.show.GankActivity
import com.xuie.imaginary.util.DateUtils

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 *
 * @author Jie Xu
 */
class MeizhiFragment : Fragment() {

  private val meiZhiAdapter = MeiZhiAdapter(ArrayList())
  private lateinit var binding: FragmentMeizhiBinding
  private lateinit var meiZhiViewModule: MeiZhiViewModule

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    Log.d(TAG, "onCreateView")
    binding = FragmentMeizhiBinding.inflate(inflater, container, false)
    meiZhiViewModule = MainActivity.obtainViewModel(activity!!)
    binding.viewmodule = meiZhiViewModule
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Log.d(TAG, "onViewCreated")
    val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    binding.recyclerView.layoutManager = layoutManager
    binding.recyclerView.adapter = meiZhiAdapter

    meiZhiAdapter.setOnItemClickListener { adapter, v, position ->
      val fl = adapter.data[position] as BaseBean
      Log.d(TAG, "position:" + position + ", data:" + fl.toString())
      val dateString = DateUtils.getDate(fl.publishedAt!!)
      val intent = Intent(activity, GankActivity::class.java)
      intent.putExtra("date", dateString)
      intent.putExtra("image", fl.url)
      val options = ActivityOptions.makeSceneTransitionAnimation(activity, v, "shareObject")
      ActivityCompat.startActivity(activity!!, intent, options.toBundle())
    }
    meiZhiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
    meiZhiAdapter.isFirstOnly(true)

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
