package com.xfinity.gotviewer.activities;

import android.widget.Toast;

import com.xfinity.xfinityapp.activities.MainActivity;
import com.xfinity.xfinityapp.network.CharacterRestAPI;

/**
 * Created by Ihsanulhaq on 3/11/2016.
 */
public class GOTMainActivity extends MainActivity {

    @Override
    protected void fetchData() {
        new CharacterRestAPI(this).getListOfGOTCharacters();
        Toast.makeText(this, "GOT", Toast.LENGTH_LONG).show();
    }
}