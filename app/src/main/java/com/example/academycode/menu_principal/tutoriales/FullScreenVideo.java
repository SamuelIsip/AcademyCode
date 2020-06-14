package com.example.academycode.menu_principal.tutoriales;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.academycode.R;
import com.example.academycode.menu_principal.MenuPrincipal;

public class FullScreenVideo extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);

        webView = findViewById(R.id.full_video);

        String url_video = getIntent().getStringExtra("url_video");

        webView.loadUrl(url_video);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), TutorialesVideo.class));
        finish();
    }


}
