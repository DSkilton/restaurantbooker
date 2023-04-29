package com.restaurantbooker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantbooker.R;
import com.google.android.material.textfield.TextInputLayout;
import com.restaurantbooker.data.RoomUserRepository;
import com.restaurantbooker.data.UserRepository;
import com.restaurantbooker.data.UserRepositoryImpl;
import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.ui.viewmodels.UserViewModel;
import com.restaurantbooker.data.UserRepositoryImpl;
import android.util.Log;

public class WelcomeActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignUp;
    private TextView tvAlreadyRegistered;
    private Button btnLogin;
    private TextView tvErrorMessage;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initialize the AppDatabase instance
        AppDatabase appDatabase = AppDatabase.getInstance(this);

        // Initialize the UserRepository instance
        UserRepository userRepository = new RoomUserRepository(getApplication());
        Log.d("WelcomeActivity", "userRepository: " + userRepository.getClass().getSimpleName());

        UserViewModelFactory factory = new UserViewModelFactory(userRepository);
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignUp = findViewById(R.id.btn_signup);
        tvAlreadyRegistered = findViewById(R.id.tv_already_registered);
        btnLogin = findViewById(R.id.btn_login);
        tvErrorMessage = findViewById(R.id.tv_error_message);

        tvAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show login UI and hide sign up UI
                btnSignUp.setVisibility(View.GONE);
                tvAlreadyRegistered.setVisibility(View.GONE);

                btnLogin.setVisibility(View.VISIBLE);
                etEmail.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Validate user input
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    tvErrorMessage.setText("Please enter both email and password.");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }

                // Authenticate the user
                userViewModel.getUserByEmail(email).observe(WelcomeActivity.this, new Observer<UserEntity>() {
                    @Override
                    public void onChanged(UserEntity existingUser) {
                        if (existingUser != null && existingUser.getPassword().equals(password)) {
                            // User authenticated, proceed to dashboard activity
                            Intent intent = new Intent(WelcomeActivity.this, DashboardActivity.class);
                            saveUserEmail(email);
                            startActivity(intent);
                            finish();
                        } else {
                            // Invalid credentials, show error message
                            tvErrorMessage.setText("Invalid email or password.");
                            tvErrorMessage.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Validate user input
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    tvErrorMessage.setText("Please enter email and password.");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }

                // Check if the email already exists in the database
                Log.d("WelcomeActivity", "Checking if email exists: " + email);
                userViewModel.getUserByEmail(email).observe(WelcomeActivity.this, new Observer<UserEntity>() {
                    @Override
                    public void onChanged(UserEntity existingUser) {
                        Log.d("WelcomeActivity", "getUserByEmail onChanged triggered");
                        if (existingUser != null) {
                            // Email already exists, show error message
                            Log.d("WelcomeActivity", "Email already exists");
                            tvErrorMessage.setText(getString(R.string.email_already_exists_error));
                            tvErrorMessage.setVisibility(View.VISIBLE);
                        } else {
                            // Email does not exist, insert the new user into the database
                            UserEntity newUser = new UserEntity(email, password);
                            userViewModel.insertUser(newUser).observe(WelcomeActivity.this, new Observer<Long>() {
                                @Override
                                public void onChanged(Long aLong) {
                                    if (aLong != null && aLong > 0) {
                                        Log.d("WelcomeActivity", "User inserted with ID: " + aLong);
                                        String message = getString(R.string.signup_success_message, email);
                                        showMessage(message);

                                        // Navigate to the DashboardActivity after successful registration
                                        Intent intent = new Intent(WelcomeActivity.this, DashboardActivity.class);
                                        saveUserEmail(email);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Log.d("WelcomeActivity", "Error inserting user");
                                        tvErrorMessage.setText(getString(R.string.signup_error_message));
                                        tvErrorMessage.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(WelcomeActivity.this, message, Toast.LENGTH_LONG).show();

    }

    private void saveUserEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", email);
        editor.apply();
    }
}
