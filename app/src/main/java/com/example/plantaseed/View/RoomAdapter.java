package com.example.plantaseed.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.Model.RoomWithPlants;
import com.example.plantaseed.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<RoomWithPlants> roomList = new ArrayList<>();
    private PlantAdapter.ItemClickListener listener;
    private ItemClickListener itemClickListener;

    public RoomAdapter(PlantAdapter.ItemClickListener listener, ItemClickListener clickListener)
    {
        this.listener = listener;
        this.itemClickListener = clickListener;
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_item, viewGroup, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        RoomWithPlants room = roomList.get(position);
        holder.roomTitle.setText(room.getRoom().getRoomName());
        if(room.room.getRoomId() == 1)
        {
            holder.deleteRoom.setVisibility(View.GONE);
        }
        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.room.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(room.getPlants().size());

        // Create sub item view adapter
        PlantAdapter plantAdapter = new PlantAdapter(room.getPlants(), new PlantAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Plant plant, View view) {
                listener.onItemClick(plant,view);
            }

            @Override
            public void onDeleteClick(Plant plant, int position) {
                listener.onDeleteClick(plant, position);
            }
        });

        holder.room.setLayoutManager(layoutManager);
        holder.room.setAdapter(plantAdapter);
        holder.room.setRecycledViewPool(viewPool);
    }

    public interface ItemClickListener{
        void onDeleteClick(RoomWithPlants room);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public void setRooms(List<RoomWithPlants> rooms)
    {
        this.roomList = rooms;
        notifyDataSetChanged();
    }

    class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView roomTitle;
        private RecyclerView room;
        private ImageButton deleteRoom;


        RoomViewHolder(View itemView) {
            super(itemView);
            roomTitle = itemView.findViewById(R.id.roomTitle);
            room = itemView.findViewById(R.id.room);
            deleteRoom = itemView.findViewById(R.id.deleteRoom);
            deleteRoom.setOnClickListener(v -> itemClickListener.onDeleteClick(roomList.get(getAbsoluteAdapterPosition())));

        }
    }
}

