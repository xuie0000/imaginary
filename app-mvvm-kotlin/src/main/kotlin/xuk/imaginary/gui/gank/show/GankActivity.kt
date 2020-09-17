package xuk.imaginary.gui.gank.show

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xuie.imaginary.R
import com.xuie.imaginary.ViewModelFactory
import com.xuie.imaginary.base.BaseActivity
import com.xuie.imaginary.databinding.ActivityGankBinding
import java.util.*

/**
 * @author Jie Xu
 */
class GankActivity : BaseActivity() {

  private val adapter = ExpandableItemAdapter(ArrayList())
  private lateinit var mBinding: ActivityGankBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gank)

    val date = intent.getStringExtra("date")
    val imageUrl = intent.getStringExtra("image")

    Log.d(TAG, "onCreate: data - $date, image - $imageUrl")

    val gankViewModule = obtainViewModel(this)
    gankViewModule.dateString.set(date)
    gankViewModule.imageUrl.set(imageUrl)
    mBinding.viewmodule = gankViewModule

    mBinding.recyclerView.adapter = adapter
    mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    mBinding.recyclerView.isNestedScrollingEnabled = false
    adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

    gankViewModule.getGank(date)
  }

  companion object {
    private const val TAG = "GankActivity"

    fun obtainViewModel(activity: FragmentActivity): GankViewModule {
      val factory = ViewModelFactory.getInstance(activity.application)
      return ViewModelProviders.of(activity, factory).get(GankViewModule::class.java)
    }
  }
}
