package com.example.movieapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.Model.Film;
import com.example.movieapp.Model.SliderItems;
import com.example.movieapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBannerActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseDatabase db;
    private DatabaseReference bannerRef;
    private EditText name,time,year,image,genre,age;
    private Button btUpdate,btCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_banner);
        initView();
    }
    private void initView(){
        name=findViewById(R.id.etName);
        time=findViewById(R.id.etTime);
        year=findViewById(R.id.etYear);
        image=findViewById(R.id.etImage);
        genre=findViewById(R.id.etGenre);
        age=findViewById(R.id.etAge);
        btUpdate=findViewById(R.id.btUpdate);
        btCancel=findViewById(R.id.btCancel);
        btUpdate.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        db = FirebaseDatabase.getInstance();
        bannerRef = db.getReference("Banners");
    }

    @Override
    public void onClick(View v) {
        if(v==btCancel){
            finish();
        }
        if(v==btUpdate){
            String n=name.getText().toString().trim();
            String t=time.getText().toString().trim();
            String y=year.getText().toString().trim();
            String i=image.getText().toString().trim();
            String g=genre.getText().toString().trim();
            String a=age.getText().toString().trim();
            SliderItems items=new SliderItems(i,n,g,a,y,t);
            Log.d("Loi4", "onClick: "+items);
            bannerRef.child("4").setValue(items)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent=new Intent(AddBannerActivity.this,AdminActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}