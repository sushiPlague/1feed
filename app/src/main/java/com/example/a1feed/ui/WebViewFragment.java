package com.example.a1feed.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a1feed.R;
import com.example.a1feed.model.NewsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment {

    private WebView webView;
    private NewsViewModel viewModel;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance() {
        WebViewFragment fragment = new WebViewFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        webView = view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(viewModel.getWebvArticleURL().getValue());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        return view;
    }
}