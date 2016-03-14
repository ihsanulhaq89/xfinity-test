package com.xfinity.xfinityapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.fragments.DetailFragment;
import com.xfinity.xfinityapp.fragments.MainFragment;
import com.xfinity.xfinityapp.interfaces.CharacterRestAPIListener;
import com.xfinity.xfinityapp.models.CharacterResponse;
import com.xfinity.xfinityapp.models.RelatedTopic;
import com.xfinity.xfinityapp.util.Constants;

public abstract class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener, CharacterRestAPIListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData();
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.BROADCAST_EVENT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ((MainFragment) getFragmentManager()
                    .findFragmentById(R.id.main_frag)).setGridLayout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDetailFragmentInteraction(Uri uri) {

    }

    protected abstract void fetchData();

    @Override
    public void onSuccess(CharacterResponse data) {
        ((MainFragment) getFragmentManager()
                .findFragmentById(R.id.main_frag)).getmAdapter().addAll(data.getRelatedTopics());
        ((MainFragment) getFragmentManager()
                .findFragmentById(R.id.main_frag)).getmAdapter().notifyDataSetChanged();
    }

    @Override
    public void onFailure(String errorMsg) {

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            RelatedTopic data = (RelatedTopic) bundle.getSerializable(Constants.B_DATA);
            Toast.makeText(MainActivity.this, data.getTitle(), Toast.LENGTH_LONG).show();
            DetailFragment displayFrag = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.details_frag);
            if (displayFrag == null) {
                startDetailActivity(data);

            } else {
                updateDetailFragment(displayFrag, data);
            }
        }
    };

    private void updateDetailFragment(DetailFragment displayFrag, RelatedTopic data) {
        displayFrag.update(data);
    }

    private void startDetailActivity(RelatedTopic data) {
        Intent i = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.B_DATA, data);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
