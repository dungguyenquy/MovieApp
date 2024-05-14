package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText registerEmail,registerPassword;
    private TextView textLogin;
    private Button registerButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        changeLogin();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void init(){
        registerEmail=findViewById(R.id.resgister_email);
        registerPassword=findViewById(R.id.register_password);
        registerButton=findViewById(R.id.btnSignin);
        mAuth=FirebaseAuth.getInstance();
        textLogin=findViewById(R.id.text_login);
    }
    private void registerUser(){
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            registerEmail.setError("Không được để trống Password.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            registerPassword.setError("Không được để trống Password.");
            return;
        }

        if (password.length() < 6) {
            registerPassword.setError("Password phải chứa ít nhất 6 ký tự.");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công",
                                Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void changeLogin(){
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}