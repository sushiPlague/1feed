package com.example.a1feed.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a1feed.R;
import com.example.a1feed.ui.main.MainActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signInButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initComponents();
    }

    private void initComponents() {
        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);

        backButton = findViewById(R.id.signInBackButton);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInBackButton:
                Intent backIntent = new Intent(this, GetStartedActivity.class);
                startActivity(backIntent);
                finish();
                break;
            case R.id.signInButton:
                EditText usernameInput = findViewById(R.id.signInUsername);
                EditText passwordInput = findViewById(R.id.signInPassword);

                /*
                TODO: implement login (sign in)
                 */

                startMain();

                break;
            default:
                break;
        }
    }

    private void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}