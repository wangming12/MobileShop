package com.example.mobileshop.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mobileshop.R;
import com.example.mobileshop.common.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends Fragment {
    @BindView(R.id.webview)
    MyWebView myWebView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment_layout,container,false);
        //必须写。否则没用
        ButterKnife.bind(this,view);
        initWebView(view);
        myWebView.loadUrl("https://www.jd.com/");
        setSwipeRefreshLayout();
        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,android.R.color.holo_green_dark,android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myWebView.reload();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView(View view) {
        myWebView.addJavascriptInterface(this,"app");
        myWebView.setVerticalScrollBarEnabled(false);
        myWebView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        myWebView.setOnCustomScrollChange(new MyWebView.IWebViewScroll() {
            @Override
            public void onTop() {
                swipeRefreshLayout.setEnabled(true);
            }

            @Override
            public void onDown() {
                swipeRefreshLayout.setEnabled(false);
            }
        });
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
