package com.example.a1feed.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<Article>> articles;
    private MutableLiveData<News> selectedArticle = new MutableLiveData<>();

    public LiveData<List<Article>> getData() {
        if (articles == null) {
            articles = new MutableLiveData<>();
        }
        return articles;
    }

    public void fetchData(List<Article> fetchedArticles) {
        this.articles.setValue(fetchedArticles);
    }

    public void selectData(News articleItem) {
        this.selectedArticle.setValue(articleItem);
    }

    public LiveData<News> getSelected() {
        return this.selectedArticle;
    }
}
