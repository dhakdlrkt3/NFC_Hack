package com.example.bongjae.nfctest;

import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.bongjae.nfctest.R;

/**
 * Created by Bongjae on 2017-03-16.
 */

public class Ut1 extends Fragment {

    WebView webView1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ut1, container, false);


        String url = "http://www.seoul.go.kr/v2012/find.html";
        webView1 = (WebView)rootView.findViewById(R.id.webview1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setUserAgentString("Android");
        webView1.setInitialScale(70);
        webView1.getSettings().setBuiltInZoomControls(true);
        webView1.getSettings().setDisplayZoomControls(false);
        webView1.setWebViewClient(new WebViewClient()); // 이걸 안해주면 새창이 뜸
        webView1.loadUrl(url);

        webView1.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView1.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });

        return rootView;

    }
    private void webViewGoBack(){
        webView1.goBack();

    }



}