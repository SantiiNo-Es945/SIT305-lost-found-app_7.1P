package com.example.a71p;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//connect advert to data list UI
public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder> {

    Context context;
    ArrayList<Advert> advertList;

    public AdvertAdapter(Context context, ArrayList<Advert> advertList) {
        this.context = context;
        this.advertList = advertList;
    }
    //load item_advertXML
    @Override
    public AdvertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_advert, parent, false);
        return new AdvertViewHolder(view);
    }
    //put advert data into each row
    @Override
    public void onBindViewHolder(AdvertViewHolder holder, int position) {
        Advert advert = advertList.get(position);

        holder.titleTextView.setText(advert.getType() + ": " + advert.getName());
        holder.categoryTextView.setText("Category: " + advert.getCategory());
        holder.dateTextView.setText("Posted: " + advert.getDate());

        if (!advert.getImagePath().isEmpty()) {
            holder.advertImageView.setImageURI(Uri.parse(advert.getImagePath()));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdvertDetailActivity.class);
            intent.putExtra("id", advert.getId());
            intent.putExtra("type", advert.getType());
            intent.putExtra("name", advert.getName());
            intent.putExtra("phone", advert.getPhone());
            intent.putExtra("description", advert.getDescription());
            intent.putExtra("category", advert.getCategory());
            intent.putExtra("location", advert.getLocation());
            intent.putExtra("date", advert.getDate());
            intent.putExtra("imagePath", advert.getImagePath());
            context.startActivity(intent);
        });
    }
    //tell recyclerview how many adverts exist
    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public static class AdvertViewHolder extends RecyclerView.ViewHolder {

        ImageView advertImageView;
        TextView titleTextView, categoryTextView, dateTextView;

        public AdvertViewHolder(View itemView) {
            super(itemView);

            advertImageView = itemView.findViewById(R.id.advertImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}