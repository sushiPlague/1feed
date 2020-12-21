package com.example.a1feed.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a1feed.R;

public class GetStartedActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        initComponents();
    }

    private void initComponents() {
        signInButton = findViewById(R.id.startSignInButton);
        signInButton.setOnClickListener(this);

        signUpButton = findViewById(R.id.startSignUpButton);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startSignInButton:
                Intent signInIntent = new Intent(this, SignInActivity.class);
                startActivity(signInIntent);
                finish();
                break;
            case R.id.startSignUpButton:
                Intent signUpIntent = new Intent(this, SignUpActivity.class);
                startActivity(signUpIntent);
                finish();
                break;
            default:
                break;
        }
    }
}