package xuk.imaginary.gui.gank.show

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import kotlinx.android.synthetic.main.activity_gk.*
import xuk.imaginary.R
import xuk.imaginary.base.BaseActivity
import xuk.imaginary.util.convertToMei

/**
 * @author Jie Xu
 */
class GkActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gk)

    val date = intent.getStringExtra("date")
    val imageUrl = intent.getStringExtra("image")

    Log.d(TAG, "onCreate: data - $date, image - $imageUrl")

    val viewModule = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        .create(GkViewModule::class.java)

    gkDaily.load(imageUrl)

    val gkAdapter = GkAdapter()
    recyclerView.apply {
      this.adapter = gkAdapter
      layoutManager = LinearLayoutManager(this@GkActivity)
      isNestedScrollingEnabled = false
    }

    viewModule.gk.observe(this, androidx.lifecycle.Observer {
      gkAdapter.submitList(it.convertToMei())
    })

    viewModule.requestGk(date!!)
  }

  companion object {
    private const val TAG = "GkActivity"
  }
}
