package com.anglus.webviewtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.wv_news_detail);

        mWebView.loadUrl("http://www.google.com");// 加载网页链接
        // 加载本地 assets 目录下的网页
        mWebView.loadUrl("file:///android_asset/demo.html");

        // 基本设置
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);// 显示缩放按钮（wap 网页不支持）
        settings.setUseWideViewPort(true);// 支持双击缩放（wap 网页不支持）
        settings.setJavaScriptEnabled(true);// 支持 js 功能

        // 设置 WebViewClient
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "onPageStarted: " + "开始加载网页了");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished: " + "网页加载结束");
            }

            // 所有链接跳转走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "跳转链接: " + url);
                view.loadUrl(url);// 跳转链接时强制在当前 WebView 中加载

                // 此方法还有其他应用场景，比如写一个超链接 <a href="tel:110">联系我们</a>,
                // 当点击该链接时获取链接地址 tel:110，解析地址。。。。

                return true;
            }
        });

        // 设置 WebChromeClient
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 进度发生变化
                Log.d(TAG, "onProgressChanged: " + "进度" + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // 网页标题
                Log.d(TAG, "onReceivedTitle: " + "网页标题" + title);
            }
        });

        // web 加载上一页和下一页
//        mWebView.goBack();// 跳转到上个页面
//        mWebView.getNextFocusForwardId();// 跳转到下个页面
//        mWebView.canGoBack();// 是否可以跳转到上一页（如果返回 false，说明已近是第一页）
//        mWebView.canGoForward();// 是否可以跳转到下一页（如果返回 false，说明已近是最后一页）
    }
}
