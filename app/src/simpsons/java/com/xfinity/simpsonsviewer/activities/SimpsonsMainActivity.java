package com.xfinity.simpsonsviewer.activities;

import android.widget.Toast;

import com.xfinity.xfinityapp.activities.MainActivity;
import com.xfinity.xfinityapp.network.CharacterRestAPI;

public class SimpsonsMainActivity extends MainActivity {

    @Override
    protected void fetchData() {
        new CharacterRestAPI(this).getListOfSimpsonsCharacters();
        Toast.makeText(this, "Simpsons", Toast.LENGTH_LONG).show();
    }
}
