package xuk.imaginary.gui.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.anzewei.parallaxbacklayout.ParallaxBack;

import xuk.imaginary.R;
import xuk.imaginary.gui.BaseActivity;

/**
 * @author xuie
 */
@ParallaxBack
public class WebViewActivity extends BaseActivity {

    public static final String URL = "url";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.web_view);
        String url = getIntent().getStringExtra(URL);

        // Configure related browser settings
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        webView.setWebViewClient(new MyBrowser());
        // Load the initial URL
        webView.loadUrl(url);
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
