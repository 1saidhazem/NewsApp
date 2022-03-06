package com.example.retrofetnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.retrofetnews.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private RVAdapter adapter;
    public static String baseURL = "https://newsapi.org/v2/";
    private String countrySelected , categorySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mainSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countrySelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.mainSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categorySelected = adapterView.getItemAtPosition(i).toString();
                sendRequestByRetrofit(countrySelected ,categorySelected);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    private void sendRequestByRetrofit(String country ,String category) {
        API_Interface apiInterface = RetrofitClient.getInstance().create(API_Interface.class);

        apiInterface.getNews(
                country,
                category,
                "247cfe1c34b040fa9b60b78b65ecd10c"
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


}