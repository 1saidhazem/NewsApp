package com.example.retrofetnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofetnews.databinding.CustomItemBinding;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {


    private List<Articles> articlesList;
    private Context context;
    private OnClickItem listener;

    public RVAdapter(List<Articles> articlesList, Context context, OnClickItem listener) {
        this.articlesList = articlesList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, null, false);
        MyViewHolder ViewHolder = new MyViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Articles articles = articlesList.get(position);
        holder.bind(articles, position);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CustomItemBinding binding;
        Articles articles;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemBinding.bind(itemView);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClicked(articles, itemView.getId());
                }
            });

        }

        public void bind(Articles articles, int position) {
            this.articles = articles;
            binding.customItemTvSourceName.setText(articles.getSource().getName());
            binding.customItemTvName.setText(articles.getTitle());
            Glide.with(context).load(articlesList.get(position).getUrlToImage()).into(binding.customItemImg);
        }
    }
}
