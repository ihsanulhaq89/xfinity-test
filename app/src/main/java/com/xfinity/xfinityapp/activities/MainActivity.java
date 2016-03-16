package com.xfinity.xfinityapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.orm.SugarRecord;
import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.adapters.CharacterAdapter;
import com.xfinity.xfinityapp.fragments.DetailFragment;
import com.xfinity.xfinityapp.fragments.MainFragment;
import com.xfinity.xfinityapp.interfaces.CharacterRestAPIListener;
import com.xfinity.xfinityapp.models.CharacterResponse;
import com.xfinity.xfinityapp.models.Icon;
import com.xfinity.xfinityapp.models.RelatedTopic;
import com.xfinity.xfinityapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class MainActivity extends BaseActivity implements MainFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener, CharacterRestAPIListener, SearchView.OnQueryTextListener {

    private MainFragment mainFragment;
    private DetailFragment displayFrag;
    private boolean isLinear = true;
    private List<RelatedTopic> data;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            isLinear = savedInstanceState.getBoolean(Constants.LAYOUT_TYPE);
        }
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_main));
        mainFragment =  ((MainFragment) getFragmentManager()
                .findFragmentById(R.id.main_frag));

        displayFrag = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.details_frag);
        fetchLocalData();
        registerBroadcastReceiver();
    }

    protected void fetchLocalData(){
        List<RelatedTopic> dataList = RelatedTopic.listAll(RelatedTopic.class);
        if(dataList.size() > 0){
            populateUI(dataList);
        } else {
            fetchData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current  state
        savedInstanceState.putBoolean(Constants.LAYOUT_TYPE, isLinear);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    private void registerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.BROADCAST_EVENT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(displayFrag == null) {
            if (isLinear) {
                setLinear(menu.findItem(R.id.action_settings));
            } else {
                setGrid(menu.findItem(R.id.action_settings));
            }
        } else {
            menu.findItem(R.id.action_settings).setVisible(false);
        }
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
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

            if(isLinear == true) {
                setGrid(item);
            }else {
                setLinear(item);
            }
            isLinear = !isLinear;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setGrid(MenuItem item){
        mainFragment.setGridLayout();
        item.setIcon(R.drawable.ic_action_action_view_stream);
    }

    private void setLinear(MenuItem item){
        mainFragment.setLinearLayout();
        item.setIcon(R.drawable.ic_action_action_view_module);
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
        List<RelatedTopic> dataList = data.getRelatedTopics();
        for(RelatedTopic record : dataList){
            Icon icon = record.getIcon();
            record.save();
            icon.save();
        }
        populateUI(data.getRelatedTopics());
    }

    protected void populateUI(List<RelatedTopic> dataList){
        this.data = dataList;
        findViewById(R.id.progress).setVisibility(View.GONE);
        CharacterAdapter adapter = mainFragment.getmAdapter();
        adapter.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.collapseActionView(searchItem);
    }

    @Override
    public void onFailure(String errorMsg) {
        findViewById(R.id.progress).setVisibility(View.GONE);
        Toast.makeText(this, "Error: Please try again later.",Toast.LENGTH_LONG).show();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            RelatedTopic data = (RelatedTopic) bundle.getSerializable(Constants.B_DATA);
            Long id = (Long) bundle.getLong(Constants.B_ID);
            data.setId(id);
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
        bundle.putLong(Constants.B_ID, data.getId());
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<RelatedTopic> newData = filter(data, newText);
        mainFragment.getmAdapter().addAll(newData);
        mainFragment.getmAdapter().notifyDataSetChanged();
        if(newData.size() <= 0){
            Toast.makeText(this, "No Character with the name of "+ newText+" found!", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private List<RelatedTopic> filter(List<RelatedTopic> models, String query) {
        query = query.toLowerCase();

        final List<RelatedTopic> filteredModelList = new ArrayList<>();
        for (RelatedTopic model : models) {
            final String text = model.getText().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
