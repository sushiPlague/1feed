package com.example.a1feed.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.a1feed.R;
import com.example.a1feed.SharedPreferencesHandler;
import com.example.a1feed.model.Article;
import com.example.a1feed.model.News;
import com.example.a1feed.model.NewsViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsHomeFragment extends Fragment {

    private NewsViewModel viewModel;
    private LinearLayout mainLayout;
    private ProgressBar progressBar;

    public NewsHomeFragment() {
        // Required empty public constructor
    }

    public static NewsHomeFragment newInstance() {
        NewsHomeFragment fragment = new NewsHomeFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_home, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        fetchAllNews();

        viewModel.getData().observe(getViewLifecycleOwner(), news -> {
            drawNews();
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainLayout = view.findViewById(R.id.mainLayout);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void fetchAllNews() {
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        String countryCode = SharedPreferencesHandler.loadSharedPreferences(requireActivity());

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        Gson gson = new Gson();

        String url = String.format("https://newsapi.org/v2/top-headlines?country=%s&apiKey=fa9861a67e2d4a34aac625ad3257126a", countryCode);
        System.out.println("URL JE OVO: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                (JSONObject response) -> {
                    News news = gson.fromJson(String.valueOf(response), News.class);
                    viewModel.fetchData(news.getArticles());
                },
                (VolleyError error) -> {
                    VolleyLog.d(error.toString());
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };

        queue.add(request);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                if (progressBar != null && progressBar.isShown()) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void fillNews(Article article, View view) {
        ImageView articleImage = view.findViewById(R.id.articleImage);
        TextView articleTitle = view.findViewById(R.id.articleTitle);

        if (article.getUrlToImage() != null) {
            Picasso.get().load(article.getUrlToImage()).into(articleImage);
        }

        articleTitle.setText(article.getTitle());

        mainLayout.addView(view);
    }

    private void drawNews() {
        LayoutInflater inflater = getLayoutInflater();

        for (Article article : viewModel.getData().getValue()) {
            View v = inflater.inflate(R.layout.article_layout, mainLayout, false);
            fillNews(article, v);
        }
    }
}