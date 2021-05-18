package com.example.plantaseed.View;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.ImageView;
import android.widget.TextView;


import com.example.plantaseed.Model.Photo;
import com.example.plantaseed.Model.Plant;

import com.example.plantaseed.R;
import com.example.plantaseed.ViewModel.PhotoViewModel;
import com.example.plantaseed.ViewModel.PlantViewModel;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


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
        photoViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(PhotoViewModel.class);


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
        photoViewModel.getAllPhotosById(viewPlant.getPlantId()).observe(getViewLifecycleOwner(), photos -> photoAdapter.setPhotos(photos)
        );


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


        return view;
    }

    private void BuildRecyclerView() {
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        photoAdapter = new PhotoAdapter((photo, position) -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        deletePhoto(photo,position);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            };

            AlertDialog.Builder builder = buildDialog(dialogClickListener);
            AlertDialog alertDialog = builder.create();
            if(getDialogStatus()){
                alertDialog.hide();
                deletePhoto(photo,position);
            }else{
                alertDialog.show();
            }
        });
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
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

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
        File photoFile;

        try {
            photoFile = createImageFile();
            saveImage(photoFile);
        } catch (IOException ignored) {

        }
        Photo photo = new Photo(currentPhotoPath);
        photo.setId_fkPlant(viewPlant.getPlantId());
        photoViewModel.insert(photo);
    }

    private void deletePhoto(Photo photo, int position)
    {
        photoViewModel.delete(photo);
        photoAdapter.notifyItemRemoved(position);
    }

    public AlertDialog.Builder buildDialog(DialogInterface.OnClickListener clickListener)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.check_dialog, null);
        CheckBox mCheckBox = mView.findViewById(R.id.checkBox);
        mBuilder.setTitle("You are deleting an item!");
        mBuilder.setMessage("Are you sure?");
        mBuilder.setView(mView);
        mBuilder.setPositiveButton("Yes", clickListener);
        mBuilder.setNegativeButton("No", clickListener);
        mCheckBox.setOnCheckedChangeListener((compoundButton, b) -> storeDialogStatus(compoundButton.isChecked()));
        return mBuilder;
    }

    private void storeDialogStatus(boolean isChecked){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("item", isChecked);
        mEditor.apply();
    }

    private boolean getDialogStatus(){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("item", false);
    }


}