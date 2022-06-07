package com.techtown.newstest_v2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView rank;
    public ImageView imageview;
    public TextView title;
    public TextView provider;
    public TextView date;
    public LinearLayout type;
    public TextView category;

    ViewHolder(Context context, View itemView) {
        super(itemView);
        rank = itemView.findViewById(R.id.rank);
        title = itemView.findViewById(R.id.title);
        provider = itemView.findViewById(R.id.provider);
        date = itemView.findViewById(R.id.date);
        imageview = itemView.findViewById(R.id.imageview);
    }
}
