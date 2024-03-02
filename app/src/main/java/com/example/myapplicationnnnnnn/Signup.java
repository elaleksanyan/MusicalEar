package com.example.myapplicationnnnnnn;

//import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthUserCollisionException;
//import com.google.firebase.auth.FirebaseUser;
//
//public class Signup extends AppCompatActivity {
//    private static final String TAG = "Signup";
//
//    EditText signup_username;
//    EditText editTextEmail;
//    EditText editTextPassword;
//    Button buttonReg;
//    FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        signup_username = findViewById(R.id.signup_username);
//        editTextEmail = findViewById(R.id.signup_email);
//        editTextPassword = findViewById(R.id.signup_pass);
//        buttonReg = (Button)findViewById(R.id.signup_btn);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        buttonReg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email, password;
//                email = String.valueOf(editTextEmail.getText());
//                password = String.valueOf(editTextPassword.getText());
//
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(Signup.this, "Enter email", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(Signup.this, "Enter password", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (password.length() < 6) {
//                    Toast.makeText(Signup.this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    sendEmailVerification();
//                                    Toast.makeText(Signup.this, "Sign-up successful. Verification email sent.", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                                        Toast.makeText(Signup.this, "The email address is already in use by another account.", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(Signup.this, "Sign-up failed. Please try again.", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                        });
//            }
//
//            private void sendEmailVerification() {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                if (user != null) {
//                    if (!user.isEmailVerified()) {
//                        user.sendEmailVerification()
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Log.d(TAG, "Verification email sent.");
//                                        } else {
//                                            Log.e(TAG, "Failed to send verification email.", task.getException());
//                                        }
//                                    }
//                                });
//                    } else {
//                        Log.d(TAG, "User is already verified.");
//                    }
//                }
//            }
//        });
//    }
//}

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, confirmPassword;
    private Button signupButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_pass);
        signupButton = findViewById(R.id.signup_btn);
        loginRedirectText = findViewById(R.id.loginRedirect);
        confirmPassword = findViewById(R.id.signup_confirmpassword);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String confirmpass = confirmPassword.getText().toString().trim();

                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                } else if (pass.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                } else if (confirmpass.isEmpty()) {
                    confirmPassword.setError("Please confirm the password");
                } else if (!confirmpass.equals(pass)) {
                    confirmPassword.setError("Password must match");
                } else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Signup.this, "SignUp Successful", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(Signup.this, EmailVerify.class);
                                            intent.putExtra("Password", pass);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(Signup.this, "Email Verification Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(Signup.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, LoginActivity.class));
            }
        });
    }
}