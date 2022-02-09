package com.example.retrofetnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.example.retrofetnews.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    private int id;
    private String url,title,description,publishedAt,imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        url = getIntent().getStringExtra("url");
        imgUri = getIntent().getStringExtra("imgUri");
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        publishedAt = getIntent().getStringExtra("publishedAt");

        binding.detailsTvPublishedAt.setText(publishedAt);
        binding.detailsTvTitle.setText(title);
        binding.detailsTvDescription.setText(description);
        Glide.with(getBaseContext()).load(imgUri).into(binding.detailsImg);

        binding.detailsBtnSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

    }

}