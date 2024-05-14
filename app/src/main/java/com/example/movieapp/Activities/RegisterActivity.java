package com.example.movieapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.Domains.User;
import com.example.movieapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText registerEmail,registerPassword,registerName;
    private TextView textLogin;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference usersRef;
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
        db = FirebaseDatabase.getInstance();
        usersRef = db.getReference("User"); // Tham chiếu tới bảng "users"
        registerName=findViewById(R.id.register_name);
    }
    private void registerUser(){
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String name=registerName.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            registerEmail.setError("Không được để trống Password.");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            registerEmail.setError("Không được để trống Tên.");
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
                        FirebaseUser user=mAuth.getCurrentUser();
                        if (user!=null){
                            saveUsertoDatabase(user);
                        }
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
    private void saveUsertoDatabase(FirebaseUser user){
        String uid=user.getUid();
        String email=user.getEmail();
        String name=registerName.getText().toString().trim();
        User newUser=new User(uid,email,name,"guest");
        usersRef.child(uid).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User saved to database",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed to save user: " + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}