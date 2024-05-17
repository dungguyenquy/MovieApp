package com.example.movieapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Activities.UpDelBannerActivity;
import com.example.movieapp.Adapters.RecycleViewBannerAdapter;
import com.example.movieapp.Model.SliderItems;
import com.example.movieapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentBanners extends Fragment implements RecycleViewBannerAdapter.ItemListener {
    private RecycleViewBannerAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase db;
    private DatabaseReference bannerRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banners,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycleViewBanner);
        adapter=new RecycleViewBannerAdapter();
        db=FirebaseDatabase.getInstance();
        bannerRef= db.getReference("Banners");
        ArrayList<SliderItems> items=new ArrayList<>();
        bannerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(SliderItems.class));
                    }
                    if (!items.isEmpty()){
                        adapter.setList(items);
                        LinearLayoutManager manager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        adapter.setItemListener(FragmentBanners.this);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        SliderItems sliderItems=adapter.getSliderItem(position);
        Intent intent=new Intent(getActivity(), UpDelBannerActivity.class);
        intent.putExtra("sliderItems",sliderItems);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
