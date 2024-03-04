package com.example.myapplicationnnnnnn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginUsername, loginPassword;
    private Button loginButton;
    private TextView createAccountText, forgotPasswordText;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        auth = FirebaseAuth.getInstance();
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        createAccountText = findViewById(R.id.create_acc);
        rememberMe = findViewById(R.id.remember);
        forgotPasswordText = findViewById(R.id.forgetpassword);

        // Check if user previously opted for "Remember Me"
        SharedPreferences sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        boolean rememberMeChecked = sharedPref.getBoolean("rememberMe", false);

        if (rememberMeChecked) {
            String savedEmail = sharedPref.getString("username", "");
            String savedPassword = sharedPref.getString("password", "");

            if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
                autoLogin(savedEmail, savedPassword);
            }
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

//        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openPasswordReset();
//            }
//        });

        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Signup.class));
            }
        });
    }

    private void autoLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        handleLoginSuccess();
                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(LoginActivity.this, "Auto Login Failed", Toast.LENGTH_SHORT).show();
//                    }
                });
    }

    private void loginUser() {
        String email = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.toLowerCase()).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        handleLoginSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleLoginSuccess() {
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null && currentUser.isEmailVerified()) {
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

//            if (rememberMe.isChecked()) {
//                saveCredentials(loginUsername.getText().toString(), loginPassword.getText().toString());
//            }

            startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
        }
    }

//    private void openPasswordReset() {
//        Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
//        startActivity(intent);
//    }
//
//    private void saveCredentials(final String email, final String password) {
//        final WeakReference<Context> contextReference = new WeakReference<>(this);
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                Context context = contextReference.get();
//                if (context != null) {
//                    SharedPreferences sharedPref = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putString("username", email)
//                            .putString("password", password)
//                            .putBoolean("rememberMe", true)
//                            .apply();
//                }
//                return null;
//            }
//        }.execute();
//    }

}
