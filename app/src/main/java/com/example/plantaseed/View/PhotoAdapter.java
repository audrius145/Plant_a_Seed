package com.example.plantaseed.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plantaseed.Model.Photo;
import com.example.plantaseed.Model.PlantWithPhotos;
import com.example.plantaseed.Model.RoomWithPlants;
import com.example.plantaseed.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    List<PlantWithPhotos> plants = new ArrayList<>();
    int size;

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {
        PlantWithPhotos plant = plants.get(position);
        Glide.with(holder.plantPhoto).load(plant.photos.get(position).getPhotoPath()).into(holder.plantPhoto);
        size = plant.photos.size();
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public void setPhotos(List<PlantWithPhotos> plants)
    {
        this.plants = plants;
        notifyDataSetChanged();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{


        ImageButton plantPhoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plantPhoto = itemView.findViewById(R.id.photoID);
        }

    }
}
