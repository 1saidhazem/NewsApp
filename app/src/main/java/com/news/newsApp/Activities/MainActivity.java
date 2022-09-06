package com.news.newsApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.news.newsApp.Adapters.OnClickItem;
import com.news.newsApp.Adapters.RVAdapter;
import com.news.newsApp.Api.ApiClient;
import com.news.newsApp.Api.ApiInterface;
import com.news.newsApp.Models.Articles;
import com.news.newsApp.Models.News;
import com.news.newsApp.R;
import com.news.newsApp.Utils;
import com.news.newsApp.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ActivityMainBinding binding;
    public static final String API_KEY = "247cfe1c34b040fa9b60b78b65ecd10c";
    private List<Articles> articles = new ArrayList<>();
    private RVAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        initRV();
        onLoadingSwipeRefresh();

    }

    /*
        private void sendRequestByRetrofit(String country ,String category) {
            ApiInterface apiInterface = RetrofitClient.getInstance().create(ApiInterface.class);

            apiInterface.getNews(
                    country,
                    category,
                    API_KEY
            ).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    ArrayList<Articles> articles = response.body().getArticles();

                    adapter = new RVAdapter(articles, getBaseContext(), new OnClickItem() {
                        @Override
                        public void OnItemClicked(Articles articles, int id) {
                            String url = articles.getUrl();
                            Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("imgUri", articles.getUrlToImage());
                            intent.putExtra("title", articles.getTitle());
                            intent.putExtra("description", articles.getDescription());
                            intent.putExtra("publishedAt", articles.getPublishedAt());
                            startActivity(intent);
                        }
                    });

                    binding.progressBar.setVisibility(View.GONE);
                    binding.mainRV.setAdapter(adapter);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                    binding.mainRV.setLayoutManager(lm);
                    binding.mainRV.setHasFixedSize(true);
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
        }
    */

    public void LoadJson() {
        binding.swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getInstance().create(ApiInterface.class);
        String country = Utils.getCountry(); // us
        String language = Utils.getLanguage(); // en
        Call<News> call = apiInterface.getNews(country, API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    adapter = new RVAdapter(articles, MainActivity.this, new OnClickItem() {
                        @Override
                        public void onItemClickListener(View view, int position) {
                            Intent intent = new Intent(getBaseContext(),DetailsActivity.class);
                            Articles article = articles.get(position);
                            intent.putExtra("url",article.getUrl());
                            intent.putExtra("title",article.getTitle());
                            intent.putExtra("img",article.getUrlToImage());
                            intent.putExtra("date",article.getPublishedAt());
                            intent.putExtra("source",article.getSource().getName());
                            intent.putExtra("author",article.getAuthor());
                            startActivity(intent);
                        }
                    });
                    binding.mainRV.setAdapter(adapter);


//                    initListener();
                    binding.swipeRefreshLayout.setRefreshing(false);
                } else {
                    binding.swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });

    }

    @Override
    public void onRefresh() {
        LoadJson();
    }

    private void onLoadingSwipeRefresh() {
        binding.swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                LoadJson();
            }
        });
    }

    private void initRV (){
        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.mainRV.setLayoutManager(layoutManager);
        binding.mainRV.setItemAnimator(new DefaultItemAnimator());
        binding.mainRV.setNestedScrollingEnabled(false);
    }

}