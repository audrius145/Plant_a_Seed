package com.example.plantaseed.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantaseed.Model.Photo;
import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.PlantWithPhotos;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.R;
import com.example.plantaseed.ViewModel.PhotoViewModel;
import com.example.plantaseed.ViewModel.PlantViewModel;
import com.example.plantaseed.ViewModel.RoomViewModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PlantViewFragment extends Fragment {

    TextView plantName;
    TextView scientificName;
    TextView plantDesc;
    ImageView plantIMG;
    String plantJSON;
    Button updatePlant;
    Button capturePlant;
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;
    String currentPhotoPath;
    PlantViewModel plantViewModel;
    PhotoViewModel photoViewModel;
    Plant viewPlant;
    Bitmap bitmap;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_view, container, false);
        plantViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(PlantViewModel.class);
        photoViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(PhotoViewModel.class);

        plantName = view.findViewById(R.id.pname);
        plantDesc = view.findViewById(R.id.pdesc);
        plantIMG = view.findViewById(R.id.ppic);
        scientificName = view.findViewById(R.id.pscience);
        updatePlant = view.findViewById(R.id.updatePlant);
        capturePlant = view.findViewById(R.id.capturePlant);
        recyclerView = view.findViewById(R.id.photoRecyclerView);
        BuildRecyclerView();


        if (getArguments() != null && getArguments().containsKey("plantObject")) {
            plantJSON = getArguments().getString("plantObject");
            viewPlant = new Gson().fromJson(plantJSON, Plant.class);
            plantName.setText(viewPlant.getName());
            plantDesc.setText(viewPlant.getDescription());
            plantIMG.setImageURI(Uri.parse(viewPlant.getImageURI()));
            scientificName.setText(viewPlant.getScientificName());
        }

        updatePlant.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("plantObject", new Gson().toJson(viewPlant));
            Navigation.findNavController(view).navigate(R.id.updatePlantFragment, bundle);
        });
        capturePlant.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA
                }, 100);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        plantViewModel.getPlantsWithPhotos().observe(getViewLifecycleOwner(), plants ->
                photoAdapter.setPhotos(plants));


        return view;
    }
    private void BuildRecyclerView()
    {
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        photoAdapter = new PhotoAdapter();
        recyclerView.setAdapter(photoAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            bitmap = (Bitmap) data.getExtras().get("data");

            dispatchCreate();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void saveImage(File file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatchCreate() {
        File photoFile = null;

        try {
            photoFile = createImageFile();
            saveImage(photoFile);
        } catch (IOException ex) {

        }
        Photo photo = new Photo(currentPhotoPath);
        photo.setId_fkPlant(viewPlant.getPlantId());
        photoViewModel.insert(photo);
    }

}