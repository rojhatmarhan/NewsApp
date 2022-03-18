package com.ders.wheatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ders.wheatherapp.Models.NewsApiResponse;
import com.ders.wheatherapp.Models.NewsHeadLines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<NewsHeadLines> headLines;
    private SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadLines> headLines, SelectListener listener) {
        this.context = context;
        this.headLines = headLines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        NewsHeadLines hd = headLines.get(position);

        holder.text_title.setText(hd.getTitle());
        holder.text_source.setText(hd.getSource().getName());

        if(hd.getUrlToImage() != null){
            Picasso.get().load(hd.getUrlToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnNewsClicked(hd);
            }
        });
    }

    @Override
    public int getItemCount() {
        return headLines.size();
    }
}
