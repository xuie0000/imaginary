package xuk.imaginary.gui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import xuk.imaginary.R
import xuk.imaginary.databinding.ActivityWebviewBinding

/**
 * @author Jie Xu
 */
class WebViewActivity : AppCompatActivity() {

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityWebviewBinding>(this, R.layout.activity_webview)

    val url = intent.getStringExtra(URL)

    // Configure related browser settings
    binding.webView.settings.loadsImagesAutomatically = true
    binding.webView.settings.javaScriptEnabled = true
    binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    // Configure the client to use when opening URLs
    binding.webView.webViewClient = MyBrowser()
    // Load the initial URL
    binding.webView.loadUrl(url)
  }

  /**
   * Manages the behavior when URLs are loaded
   */
  private inner class MyBrowser : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
      view.loadUrl(request.url.toString())
      return super.shouldOverrideUrlLoading(view, request)
    }
  }

  companion object {
    const val URL = "url"
  }
}
