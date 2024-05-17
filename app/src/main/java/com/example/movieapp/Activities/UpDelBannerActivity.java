package com.example.movieapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieapp.Model.SliderItems;
import com.example.movieapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpDelBannerActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseDatabase db;
    private DatabaseReference bannerRef;
    private EditText name,time,year,image,genre,age;
    private SliderItems item;
    private Button btUpdate,btCancel,btDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_del_banner);
        initView();
        Log.d("Loi4", "onCreate: "+item.getName());
        name.setText(""+item.getName());
        time.setText(""+item.getTime());
        year.setText(""+item.getYear());
        image.setText(""+item.getImage());
        genre.setText(""+item.getGenre());
        age.setText(""+item.getAge());
    }
    private void initView(){
        name=findViewById(R.id.etName);
        time=findViewById(R.id.etTime);
        year=findViewById(R.id.etYear);
        image=findViewById(R.id.etImage);
        genre=findViewById(R.id.etGenre);
        age=findViewById(R.id.etAge);
        Intent intent=getIntent();
        item= (SliderItems) intent.getSerializableExtra("sliderItems");
        btUpdate=findViewById(R.id.btUpdate);
        btCancel=findViewById(R.id.btBack);
        btDelete=findViewById(R.id.btDelete);
        btUpdate.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        db = FirebaseDatabase.getInstance();
        bannerRef = db.getReference("Banners");
    }

    @Override
    public void onClick(View v) {
        if(v==btCancel){
            finish();
        }
        if (v==btDelete){
            String nametodelete=item.getName();
            bannerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        // Kiểm tra xem nút hiện tại có chứa 'name' không và giá trị của nó có bằng với nameToDelete không
                        if (snapshot.child("name").getValue(String.class).equals(nametodelete)) {
                            // Xóa nút hiện tại
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Xóa thành công
                                    Log.d("Loi5", "Xóa thành công");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xóa thất bại
                                    Log.d("Loi5", "Xóa thất bại: " + e.getMessage());
                                }
                            });
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void deleteNodeWithName(DataSnapshot snapshot, String nametodelete) {
        for(DataSnapshot issue:snapshot.getChildren()){
            if (snapshot.hasChild(nametodelete) && snapshot.child("Banners").getValue(String.class).equals(nametodelete)) {
                snapshot.getRef().removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Xóa thành công
                                Log.d("Loi5", "Xóa thành công");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Xóa thất bại
                                Log.d("Loi5", "Xóa thất bại: " + e.getMessage());
                            }
                        });
            }
        }
    }
}