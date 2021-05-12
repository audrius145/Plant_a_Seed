package com.example.plantaseed.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.PlantDAO;
import com.example.plantaseed.Model.PlantDatabase;
import com.example.plantaseed.Model.PlantWithPhotos;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PlantRepository {
    private PlantDAO plantDAO;
    private LiveData<List<Plant>> allRealPlants;
    private LiveData<List<PlantWithPhotos>> allPlants;
    private Executor executorService;
    private Handler mainThreadHandler;

    public PlantRepository(Application application)
    {
        PlantDatabase database = PlantDatabase.getInstance(application);
        plantDAO = database.plantDAO();
        allRealPlants = plantDAO.getAllPlants();
        allPlants = plantDAO.getPlantsWithPhotos();
        executorService = Executors.newFixedThreadPool(2);
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    }

    public void insert(Plant plant)
    {
        executorService.execute(() -> plantDAO.insert(plant));
    }
    public void update(Plant plant)
    {
        executorService.execute(() -> plantDAO.update(plant));

    }
    public void delete(Plant plant)
    {
        executorService.execute(() -> plantDAO.delete(plant));

    }
    public LiveData<List<Plant>> getAllPlants()
    {
        return allRealPlants;
    }

    public LiveData<List<PlantWithPhotos>> getPlantsWithPhotos()
    {
        return allPlants;
    }








}
