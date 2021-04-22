package com.example.plantaseed.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.Model.RoomWithPlants;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    RoomRepository repository;
    private LiveData<List<RoomWithPlants>> allRooms;
    private LiveData<List<Room>> allRealRooms;
    public RoomViewModel(@NonNull Application application) {
        super(application);
        repository = new RoomRepository(application);
        allRooms = repository.getAllRoomsWithPlants();
        allRealRooms = repository.getAllRooms();
    }

    public void insert(Room room)
    {
        repository.insert(room);
    }

    public void update(Room room)
    {
        repository.update(room);
    }

    public void delete(Room room)
    {
        repository.delete(room);
    }

    public LiveData<List<RoomWithPlants>> getAllRoomsWithPlants()
    {
        return allRooms;
    }

    public LiveData<List<Room>> getAllRooms()
    {
        return allRealRooms;
    }
}
