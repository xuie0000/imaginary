package xuk.imaginary.gui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*
import xuk.imaginary.R

/**
 * @author Jie Xu
 */
class WebViewActivity : AppCompatActivity() {

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_webview)

    val url = intent.getStringExtra(URL)

    webView.apply {
      // Configure related browser settings
      settings.loadsImagesAutomatically = true
      settings.javaScriptEnabled = true
      scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
      // Configure the client to use when opening URLs
      webViewClient = MyBrowser()
      // Load the initial URL
      loadUrl(url)
    }

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
