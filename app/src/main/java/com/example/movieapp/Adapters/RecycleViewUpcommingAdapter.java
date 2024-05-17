package com.example.movieapp.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Model.Film;
import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewUpcommingAdapter extends RecyclerView.Adapter<RecycleViewUpcommingAdapter.UpcommingViewHolder> {
    private List<Film> list;
    private RecycleViewUpcommingAdapter.ItemListener itemListener;

    public void setList(List<Film> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public RecycleViewUpcommingAdapter() {
        this.list = new ArrayList<>();
    }

    public void setItemListener(RecycleViewUpcommingAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }
    public Film getItem(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public RecycleViewUpcommingAdapter.UpcommingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new RecycleViewUpcommingAdapter.UpcommingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcommingViewHolder holder, int position) {
        Film item=list.get(position);
        Log.d("Loi3", String.valueOf("onBindViewHolder: "+item.getTitle()));
        holder.title.setText(item.getTitle());
        holder.time.setText(item.getTime());
        holder.year.setText(""+item.getYear());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UpcommingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title,time,year;
        public UpcommingViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textviewTitle);
            time=itemView.findViewById(R.id.textviewTime);
            year=itemView.findViewById(R.id.textviewYear);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (itemListener!=null){
                itemListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
