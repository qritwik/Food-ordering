package com.library.apple.food;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Register extends AppCompatActivity {

    WebView webView2;
    ProgressBar spinner2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        webView2 = (WebView) findViewById(R.id.web_register);
        spinner2 = (ProgressBar)findViewById(R.id.progressBar_fb);

        WebView myWebView = (WebView) findViewById(R.id.web_register);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView2.getSettings().setDomStorageEnabled(true);

        webView2.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                spinner2.setVisibility(View.GONE);
            }

            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });


        webView2.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView2.setScrollbarFadingEnabled(false);


        webView2.loadUrl("https://www.hungermela.com/accounts/signup/");


    }

    @Override
    public void onBackPressed() {

        if(webView2.canGoBack()){
            webView2.goBack();
            return;
        }
        super.onBackPressed();

    }

}
