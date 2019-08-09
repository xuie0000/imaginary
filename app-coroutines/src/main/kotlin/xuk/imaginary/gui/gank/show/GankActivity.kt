package xuk.imaginary.gui.gank.show

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_gank.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import xuk.imaginary.R
import xuk.imaginary.base.BaseActivity
import xuk.imaginary.util.GlideUtils
import java.util.*

/**
 * @author Jie Xu
 */
@ObsoleteCoroutinesApi
class GankActivity : BaseActivity() {

  private val adapter = ExpandableItemAdapter(ArrayList())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gank)

    val date = intent.getStringExtra("date")
    val imageUrl = intent.getStringExtra("image")

    Log.d(TAG, "onCreate: data - $date, image - $imageUrl")

    val viewModule = ViewModelProviders.of(this).get(GankViewModule::class.java)
    GlideUtils.loadImageMeizhi(this, imageUrl!!, gkDaily)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.isNestedScrollingEnabled = false
    adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

    viewModule.items.observe(this, androidx.lifecycle.Observer {
      adapter.replaceData(it)
    })

    viewModule.requestGank(date)
  }

  companion object {
    private const val TAG = "GankActivity"
  }
}
