package com.news.newsApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.news.newsApp.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;
    private int id;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportActionBar().hide();

        url = getIntent().getStringExtra("url");

        binding.detailsWv.setWebViewClient(new WebViewClient());
        binding.detailsWv.loadUrl(url);
        binding.progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        if (binding.detailsWv.canGoBack()) {
            binding.detailsWv.goBack();
        } else {
            super.onBackPressed();
        }
    }
}