package com.example.plantaseed.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantaseed.Model.Room;
import com.example.plantaseed.R;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<Room> roomList;

    RoomAdapter(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_item, viewGroup, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomTitle.setText(room.getRoomName());

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.room.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(room.getPlantsInRoom().size());

        // Create sub item view adapter
        PlantAdapter plantAdapter = new PlantAdapter(room.getPlantsInRoom());

        holder.room.setLayoutManager(layoutManager);
        holder.room.setAdapter(plantAdapter);
        holder.room.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }


    class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView roomTitle;
        private RecyclerView room;

        RoomViewHolder(View itemView) {
            super(itemView);
            roomTitle = itemView.findViewById(R.id.roomTitle);
            room = itemView.findViewById(R.id.room);
        }
    }
}

