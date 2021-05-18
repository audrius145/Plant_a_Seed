package com.example.plantaseed.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plantaseed.Model.Photo;

import com.example.plantaseed.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    List<Photo> photosList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public PhotoAdapter(ItemClickListener itemClickListener)
    {this.itemClickListener = itemClickListener;}


    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {

        Glide.with(holder.plantPhoto).load(photosList.get(position).getPhotoPath()).into(holder.plantPhoto);

    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }
    public void setPhotos(List<Photo> photos)
    {
        this.photosList = photos;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onDeleteClick(Photo photo, int position);
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{


        ImageView plantPhoto;
        ImageButton deletePhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plantPhoto = itemView.findViewById(R.id.photoID);
            this.deletePhoto = itemView.findViewById(R.id.deletePhoto);
            deletePhoto.setOnClickListener(v-> itemClickListener.onDeleteClick(photosList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition()));
        }

    }
}
