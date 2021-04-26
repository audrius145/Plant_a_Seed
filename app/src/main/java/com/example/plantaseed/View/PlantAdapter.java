package com.example.plantaseed.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.R;
import com.example.plantaseed.ViewModel.PlantViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {
    List<Plant> plants;
    private ItemClickListener clickListener;

    public PlantAdapter(List<Plant> plants, ItemClickListener itemClickListener) {

        this.plants = plants;
        this.clickListener  = itemClickListener;
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

        holder.itemView.setOnClickListener(view -> clickListener.onItemClick(plants.get(position),view));

    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public Plant getPlantAt(int position)
    {
        return plants.get(position);
    }


    public interface ItemClickListener{
        void onItemClick(Plant plant, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView plantImageView;
        TextView plantName, plantDescription;
        ImageView deletePlant;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plantImageView = itemView.findViewById(R.id.plantImage);
            this.plantName = itemView.findViewById(R.id.plantName);
            this.plantDescription = itemView.findViewById(R.id.plantDescription);
            this.deletePlant = (ImageView) itemView.findViewById(R.id.deletePlant);
        }
    }
}
