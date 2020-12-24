package com.example.a1feed.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.a1feed.R;
import com.example.a1feed.model.News;
import com.example.a1feed.model.NewsViewModel;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsHomeFragment extends Fragment {

    private NewsViewModel viewModel;
    private TextView labelNewsTitle; // temporary

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
        labelNewsTitle = view.findViewById(R.id.newsTitle);
    }

    private void fetchAllNews() {
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        Gson gson = new Gson();

        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=fa9861a67e2d4a34aac625ad3257126a";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                (JSONObject response) -> {
                    News news = gson.fromJson(String.valueOf(response), News.class);
                    viewModel.fetchData(news.getArticles());
                },
                (VolleyError error) -> {
                    VolleyLog.d(error.toString());
        });

        queue.add(request);
    }

    private void drawNews() {
        labelNewsTitle.setText("Dobavljeno sa servera");
    }
}