package com.example.plantaseed.View;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.R;
import com.example.plantaseed.ViewModel.RoomViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;


public class NewItemFragment extends Fragment {

    ImageView plantPhoto;
    Spinner spinner;
    List<Room> rooms;
    Button takePhoto;
    String currentPhotoPath;
    Button addButton;
    EditText plantName;
    EditText plantDescription;
    Bitmap bitmap;
    View view;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    public NewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_item, container, false);
        RoomViewModel roomViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RoomViewModel.class);


        addButton = view.findViewById(R.id.addButton);
        plantPhoto = view.findViewById(R.id.plantImage);
        takePhoto = view.findViewById(R.id.take_photo);
        plantDescription = view.findViewById(R.id.plant_description);
        spinner = view.findViewById(R.id.spinner_room);
        plantName = view.findViewById(R.id.plant_name);
        rooms = new ArrayList<>();
        ArrayAdapter<Room> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, rooms);
        spinner.setAdapter(adapter);

        takePhoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA
                }, 100);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
        addButton.setOnClickListener(v -> {
            dispatchCreate();
        });
        roomViewModel.getAllRooms().observe(getViewLifecycleOwner(), rooms -> {
            adapter.clear();
            adapter.addAll(rooms);
            adapter.notifyDataSetChanged();
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            bitmap = capturedImage;
            plantPhoto.setImageBitmap(capturedImage);
        }
    }

    public Room getSelectedRoom(View view)
    {
        Room room = (Room) spinner.getSelectedItem();
        return room;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void saveImage(File file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            plantPhoto.invalidate();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatchCreate() {
        File photoFile = null;
        Plant plant;
        try {
            photoFile = createImageFile();
            saveImage(photoFile);
        } catch (IOException ex) {

        }

        if (photoFile.length() == 0) {
            plant = new Plant(plantName.getText().toString(), "", plantDescription.getText().toString(), "");
            plant.setId_fkRoom(getSelectedRoom(getView()).getRoomId());
        } else {
            plant = new Plant(plantName.getText().toString(), "", plantDescription.getText().toString(), currentPhotoPath);
            plant.setId_fkRoom(getSelectedRoom(getView()).getRoomId());
        }


        Bundle bundle = new Bundle();
        bundle.putString("plantObject", new Gson().toJson(plant));
        Navigation.findNavController(view).navigate(R.id.plantsFragment, bundle);


    }
}









