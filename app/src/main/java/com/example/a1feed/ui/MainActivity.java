package com.example.a1feed.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.a1feed.R;
import com.example.a1feed.model.NewsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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
                default:
                    return false;
            }
        });
    }

}