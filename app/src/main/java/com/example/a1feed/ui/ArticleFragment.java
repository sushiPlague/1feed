package com.example.a1feed.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a1feed.R;
import com.example.a1feed.model.Article;
import com.example.a1feed.model.NewsViewModel;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    private OnFullNewsInteractionListener listener;
    private NewsViewModel viewModel;
    private ImageView imageArticle;
    private TextView labelArticleTitle;
    private TextView labelArticleAuthor;
    private TextView labelArticlePublishedAt;
    private TextView labelArticleContent;
    private Button buttonReadFull;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance() {
        ArticleFragment fragment = new ArticleFragment();

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
        View v = inflater.inflate(R.layout.fragment_article, container, false);

        imageArticle = v.findViewById(R.id.imageArticle);
        labelArticleTitle = v.findViewById(R.id.labelArticleTitle);
        labelArticleAuthor = v.findViewById(R.id.labelArticleAuthor);
        labelArticlePublishedAt = v.findViewById(R.id.labelArticlePublishedAt);
        labelArticleContent = v.findViewById(R.id.labelArticleContent);
        buttonReadFull = v.findViewById(R.id.buttonReadFull);


        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        viewModel.getSelectedArticle().observe(getViewLifecycleOwner(), article -> {
            if (article.getUrlToImage() != null) {
                Picasso.get().load(article.getUrlToImage()).into(imageArticle);
            }

            labelArticleTitle.setText(article.getTitle());
            labelArticleAuthor.setText(article.getAuthor());

            if (article.getPublishedAt() != null) {
                labelArticlePublishedAt.setText(article.getPublishedAt().split("T")[0]);
            } else {
                labelArticlePublishedAt.setText(article.getPublishedAt());
            }

            if (article.getContent() != null) {
                labelArticleContent.setText(article.getContent().split(Pattern.quote("["))[0]);
            } else {
                labelArticleContent.setText(article.getContent());
            }

            buttonReadFull.setOnClickListener((View v1) -> {
                if (listener != null) {
                    listener.showFullArticle(article.getUrl());
                }
            });
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnFullNewsInteractionListener) {
            listener = (OnFullNewsInteractionListener) context;
        } else {
            throw new RuntimeException(String.format("Interface not implemented in %s.", context.toString()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }


    public interface OnFullNewsInteractionListener {
        void showFullArticle(String articleURL);
    }
}