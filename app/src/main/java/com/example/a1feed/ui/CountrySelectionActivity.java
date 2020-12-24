package com.example.a1feed.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.a1feed.R;
import com.example.a1feed.SharedPreferencesHandler;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

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
                        .setPositiveButton(R.string.select, (dialog, which) -> {
                            getCountryCode(selected);
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

    private void getCountryCode(String selectedCountry) {
        if (selectedCountry != null) {
            Gson gson = new Gson();

            RequestQueue queue = Volley.newRequestQueue(this);

            String url = String.format("https://restcountries.eu/rest/v2/name/%s?fullText=true&fields=alpha2Code", selectedCountry.toLowerCase());

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    (JSONArray response) -> {
                        try {
                            selected = gson.fromJson(String.valueOf(response.getJSONObject(0).getString("alpha2Code").toLowerCase()), String.class);
                            SharedPreferencesHandler.saveSharedPreferences(CountrySelectionActivity.this, selected);

                            Intent countrySelection = new Intent(CountrySelectionActivity.this, MainActivity.class);

                            startActivity(countrySelection);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        }
    }
}