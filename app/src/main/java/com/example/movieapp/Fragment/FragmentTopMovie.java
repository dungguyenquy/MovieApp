package com.example.movieapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Activities.UpDelBannerActivity;
import com.example.movieapp.Adapters.RecycleViewTopMovieAdapter;
import com.example.movieapp.Model.Film;
import com.example.movieapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentTopMovie extends Fragment implements RecycleViewTopMovieAdapter.ItemListener {
    private RecycleViewTopMovieAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase db;
    private DatabaseReference topmovieRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topmovie,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycleViewTopmovie);
        adapter=new RecycleViewTopMovieAdapter();
        db=FirebaseDatabase.getInstance();
        topmovieRef= db.getReference("Items");
        ArrayList<Film> list=new ArrayList<>();
        topmovieRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Film.class));
                    }
                    if (!list.isEmpty()){
                        adapter.setList(list);
                        LinearLayoutManager manager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        adapter.setItemListener(FragmentTopMovie.this);
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
        Film film=adapter.getItem(position);
        Intent intent=new Intent(getActivity(), UpDelBannerActivity.class);
        intent.putExtra("film",film);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
