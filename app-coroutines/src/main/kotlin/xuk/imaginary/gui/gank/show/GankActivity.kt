package xuk.imaginary.gui.gank.show

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_gank.*
import xuk.imaginary.R
import xuk.imaginary.ViewModelFactory
import xuk.imaginary.base.BaseActivity
import xuk.imaginary.util.GlideUtils
import java.util.*

/**
 * @author Jie Xu
 */
class GankActivity : BaseActivity() {

  private val adapter = ExpandableItemAdapter(ArrayList())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gank)

    val date = intent.getStringExtra("date")
    val imageUrl = intent.getStringExtra("image")

    Log.d(TAG, "onCreate: data - $date, image - $imageUrl")

    val viewModule = obtainViewModel(this)
    GlideUtils.loadImageMeizhi(this, imageUrl!!, gkDaily)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.isNestedScrollingEnabled = false
    adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

    viewModule.multiItems.observe(this, androidx.lifecycle.Observer {
      adapter.replaceData(it)
    })

    viewModule.requestGank(date)
  }

  companion object {
    private const val TAG = "GankActivity"

    fun obtainViewModel(activity: FragmentActivity): GankViewModule {
      val factory = ViewModelFactory.getInstance(activity.application)
      return ViewModelProviders.of(activity, factory).get(GankViewModule::class.java)
    }
  }
}
