package com.example.jeson.webviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.web_view);
        /*
            使用getSetting()方法可以去设置一些浏览器的属性
                getJavaScriptEnabled方法时让WebView支持JavaScript脚本
         */
        webView.getSettings().setJavaScriptEnabled(true);
        /*
            调用setWebViewClient方法并传入WebViewClient实例，
                作用是：当需要从一个网页跳转到另一个网页时，目标网页仍在WebView中显示，而不是打开系统浏览器
         */
        webView.setWebViewClient(new WebViewClient());
        //传入网址
        webView.loadUrl("http://www.baidu.com");
    }
}
