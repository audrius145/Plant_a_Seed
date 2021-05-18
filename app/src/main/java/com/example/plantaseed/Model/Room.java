package com.example.plantaseed.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_room")
public class Room {
    @PrimaryKey(autoGenerate = true)
    private int roomId;
    private String roomName;
    public Room(String roomName)
    {
        this.roomName = roomName;
    }
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String toString()
    {
        return roomName;
    }






}
