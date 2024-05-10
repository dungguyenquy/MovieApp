package com.example.movieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.Activities.DetailActivity;
import com.example.movieapp.Domains.Film;
import com.example.movieapp.R;

import java.util.ArrayList;

public class FlimListAdapter extends RecyclerView.Adapter<FlimListAdapter.Viewholder> {
    Context context;
    ArrayList<Film> items;

    public FlimListAdapter(ArrayList<Film> items) {
        this.items = items;
    }

    @NonNull
    @Override

    public FlimListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.film_viewholder,parent,false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FlimListAdapter.Viewholder holder, int position) {
        holder.titleTxt.setText(items.get(holder.getAdapterPosition()).getTitle());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop(),new RoundedCorners(30));
        Glide.with(context)
                .load(items.get(position).getPoster())
                .apply(requestOptions)
                .into(holder.pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("object",items.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView pic;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.nameTxt);
            pic=itemView.findViewById(R.id.pic);
        }
    }
}