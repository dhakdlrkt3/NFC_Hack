package com.example.bongjae.nfctest;

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

/**
 * Created by Bongjae on 2017-04-06.
 */

public class Ut2 extends Fragment {

    WebView webView2;

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

        View rootView = inflater.inflate(R.layout.ut2, container, false);


        String url = "https://www.lost112.go.kr/";
        webView2 = (WebView)rootView.findViewById(R.id.webview2);
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.getSettings().setUserAgentString("Android");
        webView2.setInitialScale(70);
        webView2.getSettings().setBuiltInZoomControls(true);
        webView2.getSettings().setDisplayZoomControls(false);
        webView2.setWebViewClient(new WebViewClient()); // 이걸 안해주면 새창이 뜸
        webView2.loadUrl(url);

        webView2.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView2.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });

        return rootView;

    }
    private void webViewGoBack(){
        webView2.goBack();

    }



}