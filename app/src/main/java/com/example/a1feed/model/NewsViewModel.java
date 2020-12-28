package com.example.a1feed.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<Article>> articles;
    private MutableLiveData<Article> selectedArticle = new MutableLiveData<>();
    private MutableLiveData<String> webvArticleURL = new MutableLiveData<>();

    public LiveData<List<Article>> getNews() {
        if (articles == null) {
            articles = new MutableLiveData<List<Article>>();
        }

        return articles;
    }

    public void fetchData(List<Article> fetchedArticles) {
        this.articles.setValue(fetchedArticles);
    }

    public void selectArticle(Article article) {
        this.selectedArticle.setValue(article);
    }

    public LiveData<Article> getSelectedArticle() {
        return this.selectedArticle;
    }

    public MutableLiveData<String> getWebvArticleURL() {
        return webvArticleURL;
    }

    public void setWebvArticleURL(String webvArticleURL) {
        this.webvArticleURL.setValue(webvArticleURL);
    }
}
