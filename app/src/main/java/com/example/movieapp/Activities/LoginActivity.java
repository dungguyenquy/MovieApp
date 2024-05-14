package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private TextView textSignup;
    private Button btlogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        changeSignup();
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void init(){
        username=findViewById(R.id.inputUsername);
        password=findViewById(R.id.inputPassword);
        btlogin=findViewById(R.id.btnLogin);
        mAuth=FirebaseAuth.getInstance();
        textSignup=findViewById(R.id.text_signup);
    }
    private void login(){
        String checkmail=username.getText().toString().trim();
        String checkpass=password.getText().toString().trim();
        System.out.println(checkmail);
        Log.d("Loi","Checkmail:"+checkmail);
        if(TextUtils.isEmpty(checkmail)){
            username.setText("Email is required");
            return;
        }
        if(TextUtils.isEmpty(checkpass)){
            password.setText("Password is required");
            return;
        }
        mAuth.signInWithEmailAndPassword(checkmail,checkpass)
                .addOnCompleteListener(this,task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user=mAuth
                                .getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Authentication successful.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this, IntroActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void changeSignup(){
        textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}