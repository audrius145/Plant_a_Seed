package com.example.plantaseed.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantaseed.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView plantImageView;
    TextView plantName, plantDescription;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        this.plantImageView = itemView.findViewById(R.id.plantImage);
        this.plantName = itemView.findViewById(R.id.plantName);
        this.plantDescription = itemView.findViewById(R.id.plantDescription);
    }
}
