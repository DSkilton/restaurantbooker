package com.restaurantbooker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantbooker.R;
import com.restaurantbooker.ui.viewmodels.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel signUpViewModel;
    private EditText etEmail, etPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignUp = findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        } else {
            signUpViewModel.signUpUser(email, password).observe(this, userEntity -> {
                if (userEntity != null) {
                    // Sign up successful, navigate to LoginActivity
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close SignUpActivity
                } else {
                    // Sign up failed, show an error message
                    Toast.makeText(SignUpActivity.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
