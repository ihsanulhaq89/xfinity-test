package com.xfinity.simpsonsviewer.activities;

import android.widget.Toast;

import com.xfinity.xfinityapp.activities.MainActivity;
import com.xfinity.xfinityapp.network.NetworkController;

public class SimpsonsMainActivity extends MainActivity {


    @Override
    protected void fetchData() {

        new NetworkController().getListOfCharacters("simpsons+characters");
        Toast.makeText(this, "Simpsons", Toast.LENGTH_LONG).show();
    }
}
