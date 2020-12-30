package com.example.a1feed.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsHomeFragment extends Fragment {

    private OnNewsInteractionListener listener;
    private NewsViewModel viewModel;
    private LinearLayout mainLayout;
    private ProgressBar progressBar;
    private SearchView searchNews;

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

        mainLayout = v.findViewById(R.id.mainLayout);
        progressBar = v.findViewById(R.id.progressBar);
        searchNews = v.findViewById(R.id.searchNews);
        searchNews.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query == "") {
                    fetchAllNews();
                } else {
                    search(query);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        fetchAllNews();

        viewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            drawNews();
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNewsInteractionListener) {
            listener = (OnNewsInteractionListener) context;
        } else {
            throw new RuntimeException(String.format("Interface not implemented in %s.", context.toString()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    private void fetchAllNews() {
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        String countryCode = SharedPreferencesHandler.loadSharedPreferences(requireActivity());

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        Gson gson = new Gson();

        String url = String.format("https://newsapi.org/v2/top-headlines?country=%s&apiKey=fa9861a67e2d4a34aac625ad3257126a", countryCode);

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
        TextView articleTitle = view.findViewById(R.id.labelNewsTitle);

        if (article.getUrlToImage() != null) {
            Picasso.get().load(article.getUrlToImage()).into(articleImage);
        }

        articleTitle.setText(article.getTitle());

        mainLayout.addView(view);
    }

    private void drawNews() {
        mainLayout.removeAllViews();

        LayoutInflater inflater = getLayoutInflater();

        for (Article article : viewModel.getNews().getValue()) {
            View v = inflater.inflate(R.layout.article_layout, mainLayout, false);

            v.setOnClickListener((View view) -> {
                if (listener != null) {
                    listener.articleSelected(article);
                }
            });

            fillNews(article, v);
        }
    }

    private void search(String searchBy) {
        String countryCode = SharedPreferencesHandler.loadSharedPreferences(requireActivity());

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        Gson gson = new Gson();

        String url = String.format("https://newsapi.org/v2/everything?q=%s&sortBy=popularity&apiKey=fa9861a67e2d4a34aac625ad3257126a", searchBy.trim());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                (JSONObject response) -> {
                    System.out.println("RADI HTTP URL: " + url + "\n");
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

    public interface OnNewsInteractionListener {
        void articleSelected(Article article);
    }
}