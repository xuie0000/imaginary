package com.xuie.imaginary.gui.web

import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.xuie.imaginary.R
import com.xuie.imaginary.databinding.ActivityWebviewBinding

/**
 * @author Jie Xu
 */
class WebViewActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityWebviewBinding>(this, R.layout.activity_webview)

    val url = intent.getStringExtra(URL)

    // Configure related browser settings
    binding.webView.getSettings().setLoadsImagesAutomatically(true)
    binding.webView.getSettings().setJavaScriptEnabled(true)
    binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
    // Configure the client to use when opening URLs
    binding.webView.setWebViewClient(MyBrowser())
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

  override fun onBackPressed() {
    //        if (webView.canGoBack()) {
    //            webView.goBack()
    //        } else {
    super.onBackPressed()
    //        }
  }

  companion object {

    val URL = "url"
  }
}
