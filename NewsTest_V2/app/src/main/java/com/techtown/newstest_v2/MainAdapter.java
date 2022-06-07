package com.techtown.newstest_v2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder>{

    private ArrayList<Item> arrayList;
    private int i=1;

    public MainAdapter() {
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Log.e("TAG", "position = " + position);
        holder.onBind(arrayList.get(position));
    }

    public void setArrayList(ArrayList<Item> list){
        Log.e("E","title"+list);
        this.arrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (null != arrayList) {
            return arrayList.size();
        }
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
         TextView date;
         TextView title;
         TextView provider;
         TextView rank;
         ImageView imageView;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            provider = (TextView) itemView.findViewById(R.id.provider);
            rank = (TextView) itemView.findViewById(R.id.rank);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
        }

        public void onBind(Item item) {
            if(i>=6) i=1;
            Log.e("E","image : "+item.image);
            rank.setText(Integer.toString(i));
            title.setText(item.title);
            date.setText(item.date);
            provider.setText(item.provider);
            Glide.with(itemView.getContext())
                    .load(item.image)
                    .into(imageView);
            i++;
        }
    }
}

