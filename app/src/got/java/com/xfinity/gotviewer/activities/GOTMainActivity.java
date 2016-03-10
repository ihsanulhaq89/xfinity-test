package com.xfinity.gotviewer.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.xfinity.xfinityapp.activities.MainActivity;
import com.xfinity.xfinityapp.network.NetworkController;

/**
 * Created by Ihsanulhaq on 3/11/2016.
 */
public class GOTMainActivity extends MainActivity {

    @Override
    protected void fetchData() {
        new NetworkController().getListOfCharacters("game+of+thrones+characters");
        Toast.makeText(this, "GOT", Toast.LENGTH_LONG).show();
    }
}