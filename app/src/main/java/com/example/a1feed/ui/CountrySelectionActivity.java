package com.example.a1feed.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1feed.R;
import com.example.a1feed.SharedPreferencesHandler;

import java.util.ArrayList;

public class CountrySelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSelectCountry;
    private String[] countries;
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_selection);

        initComponents();
    }

    private void initComponents() {
        buttonSelectCountry = findViewById(R.id.buttonSelectCountry);
        buttonSelectCountry.setOnClickListener(this);

        countries = getResources().getStringArray(R.array.countries);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSelectCountry:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Countries")
                        .setSingleChoiceItems(R.array.countries, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = countries[which];
                            }
                        })
                        .setCancelable(false)
                        .setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferencesHandler.saveSharedPreferences(CountrySelectionActivity.this, selected);

                                Intent countrySelection = new Intent(CountrySelectionActivity.this, MainActivity.class);

                                startActivity(countrySelection);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = null;
                            }
                        });

                builder.create().show();

        }
    }
}