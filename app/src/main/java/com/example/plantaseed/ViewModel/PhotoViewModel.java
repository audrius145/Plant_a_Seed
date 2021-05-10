package com.example.plantaseed.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.plantaseed.Model.Photo;
import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.PlantWithPhotos;

import java.util.List;

public class PhotoViewModel extends AndroidViewModel {
    PhotoRepository repository;
    private LiveData<List<Photo>> allPhotos;

    public PhotoViewModel(@NonNull Application application) {
        super(application);

        repository = new PhotoRepository(application);
        allPhotos = repository.getAllPhotos();
    }

    public void insert(Photo photo)
    {
        repository.insert(photo);
    }

    public void update(Photo photo)
    {
        repository.update(photo);
    }

    public void delete(Photo photo)
    {
        repository.delete(photo);
    }

    public LiveData<List<Photo>> getAllPhotos()
    {
        return allPhotos;
    }

}
