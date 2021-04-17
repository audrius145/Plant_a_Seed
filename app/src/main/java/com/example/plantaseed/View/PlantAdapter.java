package com.example.plantaseed.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.R;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<ViewHolder> {



    List<Plant> plants;

    public PlantAdapter(List<Plant> plants) {

        this.plants = plants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plant_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.plantName.setText(plants.get(position).getName());
        holder.plantDescription.setText(plants.get(position).getDescription());
        if(plants.get(position).getImageURI().equals(""))
        {
            holder.plantImageView.setImageResource(R.mipmap.ic_image_placeholder_plant);
        }
        else{
            Glide.with(holder.plantImageView).load(plants.get(position).getImageURI()).into(holder.plantImageView);
        }


    }

    @Override
    public int getItemCount() {
        return plants.size();
    }
}
