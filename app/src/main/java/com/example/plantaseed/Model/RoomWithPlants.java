package com.example.plantaseed.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class RoomWithPlants {
    @Embedded
    public Room room;

    @Relation(
            parentColumn = "roomId",
            entityColumn = "plantId"
    )
    public List<Plant> plants;

    public RoomWithPlants(Room room, List<Plant> plants )
    {
        this.room = room;
        this.plants = plants;
    }

    public Room getRoom()
    {
        return room;
    }

    public List<Plant> getPlants()
    {
        return plants;
    }


}
