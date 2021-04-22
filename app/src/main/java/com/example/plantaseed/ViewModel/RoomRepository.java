package com.example.plantaseed.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.PlantDatabase;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.Model.RoomDAO;
import com.example.plantaseed.Model.RoomWithPlants;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RoomRepository {
    private RoomDAO roomDAO;
    private LiveData<List<RoomWithPlants>> allRooms;
    private LiveData<List<Room>> allRealRooms;
    private Executor executorService;
    private Handler mainThreadHandler;

    public RoomRepository(Application application)
    {
        PlantDatabase database = PlantDatabase.getInstance(application);
        roomDAO = database.roomDAO();
        allRooms = roomDAO.getRoomsWithPlants();
        allRealRooms = roomDAO.getAllRooms();
        executorService = Executors.newFixedThreadPool(2);
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    }

    public void insert(Room room)
    {
        executorService.execute(() -> roomDAO.insert(room));
    }
    public void update(Room room)
    {
        executorService.execute(() -> roomDAO.update(room));
    }
    public void delete(Room room)
    {
        executorService.execute(() -> roomDAO.delete(room));
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
