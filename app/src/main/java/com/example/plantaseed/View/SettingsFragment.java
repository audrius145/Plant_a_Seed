package com.example.plantaseed.View;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;


import com.example.plantaseed.R;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        checkBox.setChecked(getDialogStatus());

        checkBox.setOnClickListener(v -> {
            boolean checked = checkBox.isChecked();
            storeDialogStatus(checked);
        });


        return view;
    }

    private boolean getDialogStatus(){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("item", false);
    }
    private void storeDialogStatus(boolean isChecked){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("item", isChecked);
        mEditor.apply();
    }






}