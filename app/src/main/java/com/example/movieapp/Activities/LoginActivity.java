package com.example.movieapp.Activities;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private TextView textSignup;
    private Button btlogin;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference usersRef;
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
        db=FirebaseDatabase.getInstance();
        usersRef=db.getReference("User");
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
                        FirebaseUser user=mAuth.getCurrentUser();
                        if(user!=null){
                            checkRoleUser(user.getUid());
                        }
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
    private void checkRoleUser(String Uid){
        Log.d("Loi", "checkRoleUser: "+Uid);
        usersRef.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    Log.d("Loi", "onDataChange: "+role);
                    if (role != null) {
                        if (role.equals("admin")) {
                            // Chuyển sang giao diện admin
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Chuyển sang giao diện người dùng
                            Intent intent = new Intent(LoginActivity.this, IntroActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Role not found", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(LoginActivity.this, "User document not found", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                Toast.makeText(LoginActivity.this, "Failed to retrieve role.",
                        Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "Error getting documents: ", error.toException());
            }
        });
    }
}