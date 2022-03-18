package com.ders.wheatherapp.Models;

import java.io.Serializable;
import java.util.List;

public class NewsApiResponse implements Serializable {
    String status;
    Integer totalResults;
    List<NewsHeadLines> articles;

    public NewsApiResponse(String status, Integer totalResults, List<NewsHeadLines> articles) {
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

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsHeadLines> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsHeadLines> articles) {
        this.articles = articles;
    }
}
