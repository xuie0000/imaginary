package xuk.imaginary.gui.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import xuk.imaginary.R;
import xuk.imaginary.databinding.ActivityWebviewBinding;

/**
 * @author xuie
 */
public class WebViewActivity extends AppCompatActivity {

    public static final String URL = "url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);

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
