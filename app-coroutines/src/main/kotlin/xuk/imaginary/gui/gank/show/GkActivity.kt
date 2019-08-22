package xuk.imaginary.gui.gank.show

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_gk.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import xuk.imaginary.R
import xuk.imaginary.base.BaseActivity
import java.util.*

/**
 * @author Jie Xu
 */
@ObsoleteCoroutinesApi
class GkActivity : BaseActivity() {

  private val expandableItemAdapter = ExpandableItemAdapter(ArrayList())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gk)

    val date = intent.getStringExtra("date")
    val imageUrl = intent.getStringExtra("image")

    Log.d(TAG, "onCreate: data - $date, image - $imageUrl")

    val viewModule = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        .create(GkViewModule::class.java)

    gkDaily.load(imageUrl)

    expandableItemAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
    recyclerView.apply {
      this.adapter = expandableItemAdapter
      layoutManager = LinearLayoutManager(this@GkActivity)
      isNestedScrollingEnabled = false
    }

    viewModule.items.observe(this, androidx.lifecycle.Observer {
      expandableItemAdapter.replaceData(it)
    })

    viewModule.requestGk(date!!)
  }

  companion object {
    private const val TAG = "GkActivity"
  }
}
