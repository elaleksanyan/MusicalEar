//package com.example.myapplicationnnnnnn;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//
//public class PasswordResetActivity extends AppCompatActivity {
//
//    private EditText emailEditText;
//    private Button sendEmailButton;
//    private FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_password_reset);
//
//        auth = FirebaseAuth.getInstance();
//
//        emailEditText = findViewById(R.id.password_reset_email);
//        sendEmailButton = findViewById(R.id.sendemail);
//
//        sendEmailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendPasswordResetEmail();
//            }
//        });
//
//        TextView goBackToLoginTextView = findViewById(R.id.gobacklogin);
//
//        goBackToLoginTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goBackToLogin();
//            }
//        });
//    }
//
//    private void sendPasswordResetEmail() {
//        String email = emailEditText.getText().toString().trim();
//
//        if (email.isEmpty()) {
//            Toast.makeText(PasswordResetActivity.this, "Enter your email address", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        auth.sendPasswordResetEmail(email)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(PasswordResetActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(PasswordResetActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
//                            Log.e("PasswordResetActivity", "Failed to send password reset email", task.getException());
//                        }
//                    }
//                });
//    }
//
//
//    public void goBackToLogin() {
//        Intent intent = new Intent(PasswordResetActivity.this, LoginActivity.class);
//        startActivity(intent);
//
//    }
//}
