package com.news.newsApp.Models;

import java.util.ArrayList;

public class News {
    private String status;
    private int totalResults;
    private ArrayList<Articles> articles;

    public News(String status, int totalResults, ArrayList<Articles> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public ArrayList<Articles> setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
        return articles;
    }
}