package com.example.a1feed.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a1feed.R;
import com.example.a1feed.model.Article;
import com.example.a1feed.model.NewsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NewsHomeFragment.OnNewsInteractionListener, ArticleFragment.OnFullNewsInteractionListener {

    private NewsViewModel viewModel;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, NewsHomeFragment.class, null)
                    .commit();
        }

        initComponents();
    }

    private void initComponents() {
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.menu_country:
                    Intent intent = new Intent(this, CountrySelectionActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.menu_home:
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);

                    if (!(f instanceof NewsHomeFragment)) {
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment_container_view, NewsHomeFragment.class, null)
                                .commit();
                    }

                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public void articleSelected(Article article) {
        viewModel.selectArticle(article);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, ArticleFragment.class, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showFullArticle(String articleURL) {
        viewModel.setWebvArticleURL(articleURL);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, WebViewFragment.class, null)
                .addToBackStack(null)
                .commit();
    }
}