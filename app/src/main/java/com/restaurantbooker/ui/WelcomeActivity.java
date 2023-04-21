package com.restaurantbooker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantbooker.R;
import com.google.android.material.textfield.TextInputLayout;

public class WelcomeActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignUp;
    private TextView tvAlreadyRegistered;
    private Button btnLogin;
    private TextView tvErrorMessage;
    private TextInputLayout tilName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignUp = findViewById(R.id.btn_signup);
        tvAlreadyRegistered = findViewById(R.id.tv_already_registered);
        btnLogin = findViewById(R.id.btn_login);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        tilName = findViewById(R.id.til_name);

        //hide input initially
        tilName.setVisibility(View.GONE);

        tvAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show login UI and hide sign up UI
                btnSignUp.setVisibility(View.GONE);
                tvAlreadyRegistered.setVisibility(View.GONE);
                tilName.setVisibility(View.GONE);

                btnLogin.setVisibility(View.VISIBLE);
                etEmail.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show sign up UI and hide login UI
                btnLogin.setVisibility(View.GONE);
                etEmail.setVisibility(View.GONE);
                etPassword.setVisibility(View.GONE);

                btnSignUp.setVisibility(View.VISIBLE);
                tvAlreadyRegistered.setVisibility(View.VISIBLE);
                tilName.setVisibility(View.VISIBLE);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // validate user input
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    tvErrorMessage.setText("Please enter both email and password.");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }

                // proceed to dashboard activity
                Intent intent = new Intent(WelcomeActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // validate user input
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    tvErrorMessage.setText("Please enter name, email, and password.");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }

                // TODO: validate name, email, and password

                // TODO: check if user already exists

                // TODO: create new user account

                String message = getString(R.string.signup_success_message, name);
                showMessage(message);
            }
        });
    }

    private void showMessage(String message) {
        // TODO: display message to user
    }
}
