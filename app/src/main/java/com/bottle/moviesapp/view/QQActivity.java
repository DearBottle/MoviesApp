package com.bottle.moviesapp.view;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.base.BaseActivity;

public class QQActivity extends BaseActivity {

    private WebView webView;


    @Override
    protected int getResId() {
        return R.layout.activity_qq;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.webview);
    }

    @Override
    protected void initData() {
        //判断并启动QQ
        webView.getSettings().setJavaScriptEnabled(true);
        String url ="http://wpa.qq.com/msgrd?v=3&uin=3389923020&site=qq&menu=yes";
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) { //http和https协议开头的执行正常的流程
                    return super.shouldInterceptRequest(view, url);
                } else { //其他的URL则会开启一个Acitity然后去调用原生APP
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                    return null;
                }
            }
        });
    }

    @Override
    protected void initClick() {

    }
}
