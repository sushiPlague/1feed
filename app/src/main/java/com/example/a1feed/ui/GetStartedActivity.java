package com.example.a1feed.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a1feed.R;

public class GetStartedActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        initComponents();
    }

    private void initComponents() {
        buttonGetStarted = findViewById(R.id.buttonGetStarted);
        buttonGetStarted.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGetStarted:
                Intent countrySelection = new Intent(this, CountrySelectionActivity.class);
                startActivity(countrySelection);
                finish();
                break;
            default:
                break;
        }
    }
}