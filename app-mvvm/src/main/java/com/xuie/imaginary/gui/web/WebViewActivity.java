package com.xuie.imaginary.gui.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xuie.imaginary.R;
import com.xuie.imaginary.base.BaseActivity;
import com.xuie.imaginary.databinding.ActivityWebviewBinding;

/**
 * @author xuie
 */
public class WebViewActivity extends BaseActivity {

    public static final String URL = "url";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        ActivityWebviewBinding binding = getDataBinding();
        String url = getIntent().getStringExtra(URL);

        // Configure related browser settings
        binding.webView.getSettings().setLoadsImagesAutomatically(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        binding.webView.setWebViewClient(new MyBrowser());
        // Load the initial URL
        binding.webView.loadUrl(url);
    }

    @Override
    protected View[] setImmersiveView() {
        return new View[0];
    }

    /**
     * Manages the behavior when URLs are loaded
     */
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @Override
    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack()
//        } else {
        super.onBackPressed();
//        }
    }
}
