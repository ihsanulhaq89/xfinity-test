package com.xfinity.simpsonsviewer.activities;

import com.xfinity.xfinityapp.activities.MainActivity;
import com.xfinity.xfinityapp.network.CharacterRestAPI;

public class SimpsonsMainActivity extends MainActivity {

    @Override
    protected void fetchData() {
        new CharacterRestAPI(this).getListOfSimpsonsCharacters();
    }
}
