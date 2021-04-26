package com.example.plantaseed.View;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.R;
import com.google.gson.Gson;


public class PlantViewFragment extends Fragment {

    TextView plantName;
    TextView scientificName;
    TextView plantDesc;
    ImageView plantIMG;
    String plantJSON;

    public PlantViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_view, container, false);

        plantName = view.findViewById(R.id.pname);
        plantDesc = view.findViewById(R.id.pdesc);
        plantIMG = view.findViewById(R.id.ppic);
        scientificName = view.findViewById(R.id.pscience);

        if (getArguments() != null && getArguments().containsKey("plantObject")) {
            plantJSON = getArguments().getString("plantObject");
            Plant newPlant = new Gson().fromJson(plantJSON, Plant.class);
            plantName.setText(newPlant.getName());
            plantDesc.setText(newPlant.getDescription());
            plantIMG.setImageURI(Uri.parse(newPlant.getImageURI()));
            scientificName.setText(newPlant.getDescription());
        }




        return view;
    }
}