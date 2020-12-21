package com.example.a1feed.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a1feed.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signUpButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initComponents();
    }

    private void initComponents() {
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        backButton = findViewById(R.id.signUpBackButton);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBackButton:
                Intent backIntent = new Intent(this, GetStartedActivity.class);
                startActivity(backIntent);
                finish();
                break;
            case R.id.signUpButton:
                EditText firstNameInput = findViewById(R.id.signUpFirstName);
                EditText lastNameInput = findViewById(R.id.signUpLastName);
                EditText usernameInput = findViewById(R.id.signInUsername);
                EditText passwordInput = findViewById(R.id.signUpPassword);
                EditText emailInput = findViewById(R.id.signUpEmail);

                /*
                TODO: implement sign up
                 */

                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}