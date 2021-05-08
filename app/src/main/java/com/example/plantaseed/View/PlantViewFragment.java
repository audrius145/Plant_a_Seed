package com.example.plantaseed.View;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plantaseed.Model.Plant;
import com.example.plantaseed.Model.Room;
import com.example.plantaseed.R;
import com.example.plantaseed.ViewModel.RoomViewModel;
import com.google.gson.Gson;


public class PlantViewFragment extends Fragment {

    TextView plantName;
    TextView scientificName;
    TextView plantDesc;
    ImageView plantIMG;
    String plantJSON;
    Button updatePlant;

    Plant viewPlant;


    public PlantViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_view, container, false);

        plantName = view.findViewById(R.id.pname);
        plantDesc = view.findViewById(R.id.pdesc);
        plantIMG = view.findViewById(R.id.ppic);
        scientificName = view.findViewById(R.id.pscience);
        updatePlant = view.findViewById(R.id.updatePlant);


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



        return view;
    }


}