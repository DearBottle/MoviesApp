package com.bottle.moviesapp.view;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.base.BaseActivity;

public class OfficialActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected int getResId() {
        return R.layout.activity_official;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.webview);
    }

    @Override
    protected void initData() {
        webView.loadUrl("http://baidu.com");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true;
            }
        });
    }

    @Override
    protected void initClick() {

    }
}
