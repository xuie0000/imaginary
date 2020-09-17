package xuk.imaginary.gui.gank.show

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import org.kodein.di.Copy
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import xuk.imaginary.R
import xuk.imaginary.base.BaseActivity
import xuk.imaginary.databinding.ActivityGankBinding
import java.util.*

/**
 * @author Jie Xu
 */
class GankActivity : BaseActivity(), KodeinAware {

  override val kodein by retainedSubKodein(kodein(), copy = Copy.All) {
    import(gankModel)
  }

  private val adapter = ExpandableItemAdapter(ArrayList())
  private lateinit var mBinding: com.xuie.imaginary.databinding.ActivityGankBinding
  private val gankViewModule: GankViewModule by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gank)

    val date = intent.getStringExtra("date")
    val imageUrl = intent.getStringExtra("image")

    Log.d(TAG, "onCreate: data - $date, image - $imageUrl")

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
  }
}
