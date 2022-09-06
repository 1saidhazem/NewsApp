package com.news.newsApp.Models;

import java.io.Serializable;

public class Source implements Serializable {
    private String name;

    public Source(String name) {
        this.name = name;
    }

    public Source() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
