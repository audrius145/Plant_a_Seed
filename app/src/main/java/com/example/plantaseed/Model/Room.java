package com.example.plantaseed.Model;

import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "table_room")
public class Room {
    private String roomName;
    private ArrayList<Plant> plantsInRoom;

    public Room(String roomName, ArrayList<Plant> plantsInRoom) {
        this.roomName = roomName;
        this.plantsInRoom = plantsInRoom;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<Plant> getPlantsInRoom() {
        return plantsInRoom;
    }

    public void setPlantsInRoom(ArrayList<Plant> plantsInRoom) {
        this.plantsInRoom = plantsInRoom;
    }
}
