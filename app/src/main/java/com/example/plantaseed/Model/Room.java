package com.example.plantaseed.Model;

import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "table_room")
public class Room {
    private String name;
    private ArrayList<Plant> plants;

}
