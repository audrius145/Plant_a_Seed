package com.example.plantaseed.ViewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.plantaseed.Model.Photo;
import com.example.plantaseed.Model.PhotoDAO;
import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.PlantDAO;
import com.example.plantaseed.Model.PlantDatabase;
import com.example.plantaseed.Model.PlantWithPhotos;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhotoRepository {
    private PhotoDAO photoDAO;
    private LiveData<List<Photo>> allPhotos;
    private Executor executorService;
    private Handler mainThreadHandler;

    public PhotoRepository(Application application)
    {
        PlantDatabase database = PlantDatabase.getInstance(application);
        photoDAO = database.photoDAO();

        allPhotos = photoDAO.getAllPhotos();
        executorService = Executors.newFixedThreadPool(2);
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    }

    public void insert(Photo photo)
    {
        executorService.execute(() -> photoDAO.insert(photo));
    }
    public void update(Photo photo)
    {
        executorService.execute(() -> photoDAO.update(photo));

    }
    public void delete(Photo photo)
    {
        executorService.execute(() -> photoDAO.delete(photo));

    }
    public LiveData<List<Photo>> getAllPhotos()
    {
        return allPhotos;
    }
}
