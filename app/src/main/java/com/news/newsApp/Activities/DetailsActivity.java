package com.news.newsApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.news.newsApp.Models.Articles;
import com.news.newsApp.Utils;
import com.news.newsApp.databinding.ActivityDetailsBinding;


import java.util.List;

public class DetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    ActivityDetailsBinding binding;
    private boolean isHideToolbarView = false;
    private String url, img, title, date, source, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //        getSupportActionBar().hide();

        binding.collapsingToolbar.setTitle("");

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        img = intent.getStringExtra("img");
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        source = intent.getStringExtra("source");
        author = intent.getStringExtra("author");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());

        Glide.with(getBaseContext())
                .load(img)
                .apply(requestOptions)
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.backdrop);

        binding.titleOnAppbar.setText(source);
        binding.date.setText(Utils.DateFormat(date));
        binding.title.setText(title);

        binding.time.setText(source  + " \u2022 " + Utils.DateToTimeFormat(date));

        binding.detailsBtnSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            binding.dateBehavior.setVisibility(View.GONE);
            binding.titleOnAppbar.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && isHideToolbarView) {
            binding.dateBehavior.setVisibility(View.VISIBLE);
            binding.titleOnAppbar.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;

        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        supportFinishAfterTransition();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}