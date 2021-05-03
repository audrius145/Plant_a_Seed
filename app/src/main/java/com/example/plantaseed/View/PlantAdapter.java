package com.example.plantaseed.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.R;
import com.example.plantaseed.ViewModel.PlantViewModel;
import com.example.plantaseed.ViewModel.RoomViewModel;

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



    }


    @Override
    public int getItemCount() {
        return plants.size();
    }



    public interface ItemClickListener{
        void onItemClick(Plant plant, View view);
        void onDeleteClick(Plant plant);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView plantImageView;
        TextView plantName, plantDescription;
        ImageButton deletePlant;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plantImageView = itemView.findViewById(R.id.plantImage);
            this.plantName = itemView.findViewById(R.id.plantName);
            this.plantDescription = itemView.findViewById(R.id.plantDescription);
            this.deletePlant = (ImageButton) itemView.findViewById(R.id.deletePlant);
            deletePlant.setOnClickListener(v -> {
                clickListener.onDeleteClick(plants.get(getAbsoluteAdapterPosition()));
            });
            itemView.setOnClickListener(v -> {
                clickListener.onItemClick(plants.get(getAbsoluteAdapterPosition()),v);
            });
        }

    }
}
