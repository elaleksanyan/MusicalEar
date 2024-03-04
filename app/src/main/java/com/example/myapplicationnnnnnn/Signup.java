package com.example.myapplicationnnnnnn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText  signupEmail, signupPassword, confirmPassword;
    private Button signupButton;
    private TextView loginRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_pass);
        confirmPassword = findViewById(R.id.signup_confirmpassword);
        signupButton = findViewById(R.id.signup_btn);
        loginRedirect = findViewById(R.id.loginRedirect);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void registerUser() {
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.toLowerCase()).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        sendEmailVerification();
                    } else {
                        Toast.makeText(Signup.this, "Registration failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendEmailVerification() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this, "Registration successful. Please check your email for verification.", Toast.LENGTH_SHORT).show();
                            setContentView(R.layout.activity_email_verify);
                            startActivity(new Intent(Signup.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Signup.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
