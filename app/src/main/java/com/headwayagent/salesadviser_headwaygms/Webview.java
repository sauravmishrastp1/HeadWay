package com.headwayagent.salesadviser_headwaygms;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Webview extends AppCompatActivity {

    WebView webView;
    String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webview);

         webUrl = getIntent().getStringExtra("link");
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void loadWebViewLoad(android.webkit.WebView webview) {
        final ProgressDialog pd = ProgressDialog.show(Webview.this, "", "Please wait...", true);
        pd.setCancelable(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.setWebViewClient(new WebViewClient(){
            public void onReceivedError(android.webkit.WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(Webview.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon)
            {
                pd.show();
            }


            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                pd.dismiss();
            }


        });
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl(webUrl);

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (webView.canGoBack()){
                    webView.goBack();
                } else {
                    this.finish();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
