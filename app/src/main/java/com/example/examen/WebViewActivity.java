package com.example.examen;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("http://www.wikipedia.org");
    }
}