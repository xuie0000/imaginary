package com.xuie.imaginaryandroid.gui.web

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

import com.xuie.imaginaryandroid.R

import butterknife.BindView
import butterknife.ButterKnife

class WebViewActivity : AppCompatActivity() {
    @BindView(R.id.web_view)
    internal var webView: WebView? = null

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        ButterKnife.bind(this)
        val url = getIntent().getStringExtra(URL)

        // Configure related browser settings
        webView!!.getSettings().setLoadsImagesAutomatically(true)
        webView!!.getSettings().setJavaScriptEnabled(true)
        webView!!.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        // Configure the client to use when opening URLs
        webView!!.setWebViewClient(MyBrowser())
        // Load the initial URL
        webView!!.loadUrl(url)
    }

    // Manages the behavior when URLs are loaded
    private inner class MyBrowser : WebViewClient() {
        @Override
        fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.getUrl().toString())
            return super.shouldOverrideUrlLoading(view, request)
        }
    }


    @Override
    protected fun onResume() {
        super.onResume()
        val decorView = getWindow().getDecorView()
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.setSystemUiVisibility(option)
        getWindow().setStatusBarColor(Color.TRANSPARENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            )
        }
    }

    @Override
    fun onBackPressed() {
        //        if (webView.canGoBack()) {
        //            webView.goBack();
        //        } else {
        super.onBackPressed()
        //        }
    }

    companion object {

        val URL = "url"
    }
}
