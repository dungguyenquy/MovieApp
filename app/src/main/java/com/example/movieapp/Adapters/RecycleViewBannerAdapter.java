package com.example.movieapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Model.SliderItems;
import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewBannerAdapter extends RecyclerView.Adapter<RecycleViewBannerAdapter.BannerViewHolder> {
    private List<SliderItems> list;
    private ItemListener itemListener;

    public void setList(List<SliderItems> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public RecycleViewBannerAdapter() {
        this.list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }
    public SliderItems getSliderItem(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public RecycleViewBannerAdapter.BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewBannerAdapter.BannerViewHolder holder, int position) {
        SliderItems item=list.get(position);
        holder.name.setText(item.getName());
        holder.time.setText(item.getTime());
        holder.year.setText(item.getYear());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name,time,year;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvName);
            time=itemView.findViewById(R.id.tvTime);
            year=itemView.findViewById(R.id.tvYear);
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
