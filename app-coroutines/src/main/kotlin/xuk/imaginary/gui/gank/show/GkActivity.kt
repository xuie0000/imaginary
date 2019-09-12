package xuk.imaginary.gui.gank.show

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_gk.*
import xuk.imaginary.R
import xuk.imaginary.common.convertToMei
import xuk.imaginary.common.loadImage
import xuk.imaginary.gui.BaseActivity

/**
 * @author Jie Xu
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GkActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gk)

    val date = intent.getStringExtra("date")
    val imageUrl = intent.getStringExtra("image")

    println("onCreate: data - $date, image - $imageUrl")

    val viewModule = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        .create(GkViewModule::class.java)

    gkDaily.loadImage(imageUrl)

    val gkAdapter = GkAdapter()
    recyclerView.apply {
      this.adapter = gkAdapter
      layoutManager = LinearLayoutManager(this@GkActivity)
      isNestedScrollingEnabled = false
    }

    viewModule.gk.observe(this, androidx.lifecycle.Observer {
      gkAdapter.submitList(it?.convertToMei())
    })

    viewModule.requestGk(date!!)
  }
}
