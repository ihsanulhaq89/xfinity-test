package com.xfinity.xfinityapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.adapters.CharacterAdapter;
import com.xfinity.xfinityapp.fragments.DetailFragment;
import com.xfinity.xfinityapp.fragments.MainFragment;
import com.xfinity.xfinityapp.interfaces.CharacterRestAPIListener;
import com.xfinity.xfinityapp.models.CharacterResponse;
import com.xfinity.xfinityapp.models.Icon;
import com.xfinity.xfinityapp.models.RelatedTopic;
import com.xfinity.xfinityapp.util.AppConstants;
import com.xfinity.xfinityapp.util.BundleConstants;
import com.xfinity.xfinityapp.util.EventConstants;

import java.util.ArrayList;
import java.util.List;

public abstract class MainActivity extends BaseActivity implements MainFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener, CharacterRestAPIListener, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, View.OnClickListener, DrawerLayout.DrawerListener {
    private String[] drawerOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private MainFragment mainFragment;
    private DetailFragment displayFrag;
    private boolean isLinear = true;
    private List<RelatedTopic> data;
    private List<RelatedTopic> favourites;
    private Menu menu;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            isLinear = savedInstanceState.getBoolean(AppConstants.LAYOUT_TYPE);
            isLinear = savedInstanceState.getBoolean(AppConstants.DATA_TYPE);
        }
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_main));
        mainFragment = ((MainFragment) getFragmentManager()
                .findFragmentById(R.id.main_frag));

        displayFrag = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.details_frag);

        setDrawerLayout();
        fetchLocalData();
        registerBroadcastReceiver();
    }

    private void setDrawerLayout() {
        drawerOptions = getResources().getStringArray(R.array.options);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.row_drawer, drawerOptions));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(this);
        mDrawerLayout.addDrawerListener(this);
    }

    protected void fetchLocalData() {
        List<RelatedTopic> dataList = RelatedTopic.listAll(RelatedTopic.class);
        if (dataList.size() > 0) {
            populateUI(dataList, false);
        } else {
            fetchData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current  state
        savedInstanceState.putBoolean(AppConstants.LAYOUT_TYPE, isLinear);
        savedInstanceState.putBoolean(AppConstants.DATA_TYPE, isFavorite);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private void registerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mSelectionReceiver,
                new IntentFilter(EventConstants.BROADCAST_SELECTION));
        if (displayFrag != null) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mFavoriteUpdateReceiver,
                    new IntentFilter(EventConstants.BROADCAST_FAVORITE));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        showNav();
        showSecondaryTitle();
        getNavButton().setOnClickListener(this);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (displayFrag == null) {
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
        searchView.setOnSearchClickListener(searchClickListener);
        return true;
    }

    private View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isFavorite) {
                favourites = mainFragment.getmAdapter().getItems();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            if (isLinear == true) {
                setGrid(item);
            } else {
                setLinear(item);
            }
            isLinear = !isLinear;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setGrid(MenuItem item) {
        mainFragment.setGridLayout();
        item.setIcon(R.drawable.ic_action_action_view_stream);
    }

    private void setLinear(MenuItem item) {
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
        for (RelatedTopic record : dataList) {
            Icon icon = record.getIcon();
            record.save();
            icon.save();
        }
        populateUI(data.getRelatedTopics(), false);
    }

    protected void populateUI(List<RelatedTopic> dataList, boolean isFavorites) {
        if (!isFavorites) {
            this.data = dataList;
        }
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
    protected void onResume() {
        super.onResume();
        if (isFavorite) {
            fetchFavorites();
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        findViewById(R.id.progress).setVisibility(View.GONE);
        Toast.makeText(this, "Error: Please try again later.", Toast.LENGTH_LONG).show();
    }

    private BroadcastReceiver mSelectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            RelatedTopic data = (RelatedTopic) bundle.getSerializable(BundleConstants.B_DATA);
            Long id = bundle.getLong(BundleConstants.B_ID);
            int index = bundle.getInt(BundleConstants.B_INDEX);
            data.setId(id);
            if (displayFrag == null) {
                startDetailActivity(data, index);

            } else {
                updateDetailFragment(data, index);
            }
        }
    };
    private BroadcastReceiver mFavoriteUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            RelatedTopic data = (RelatedTopic) bundle.getSerializable(BundleConstants.B_DATA);
            int index = bundle.getInt(BundleConstants.B_INDEX);
            if (mainFragment != null && isFavorite) {
                if (data.getFavorite()) {
                    mainFragment.getmAdapter().getItems().add(index, data);
                } else {
                    mainFragment.getmAdapter().getItems().remove(index);
                }
                mainFragment.getmAdapter().notifyDataSetChanged();
            }

        }
    };

    private void updateDetailFragment(RelatedTopic data, int index) {
        displayFrag.update(data, index);
    }

    private void startDetailActivity(RelatedTopic data, int index) {
        Intent i = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleConstants.B_DATA, data);
        bundle.putLong(BundleConstants.B_ID, data.getId());
        bundle.putInt(BundleConstants.B_INDEX, index);
        i.putExtras(bundle);
        startActivity(i);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSelectionReceiver);
        if (displayFrag != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mFavoriteUpdateReceiver);
        }
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<RelatedTopic> currentList = isFavorite ? favourites : data;
        List<RelatedTopic> newData = filter(currentList, newText);
        mainFragment.getmAdapter().addAll(newData);
        mainFragment.getmAdapter().notifyDataSetChanged();
        if (newData.size() <= 0) {
            Toast.makeText(this, "No Character with the name of " + newText + " found!", Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        closeDrawer();
        if (i == 0) {
            isFavorite = false;
            populateUI(this.data, false);
        } else {
            isFavorite = true;
            fetchFavorites();
        }
    }

    private void fetchFavorites() {
        String[] favoriteValueArray = new String[1];
        favoriteValueArray[0] = "true";
        List<RelatedTopic> listFav = RelatedTopic.find(RelatedTopic.class, "favorite=?", "1");
        if (listFav.size() > 0) {
            populateUI(listFav, true);
            if (displayFrag != null) {
                displayFrag.update(null, 0);
            }
        } else {
            isFavorite = false;
            populateUI(this.data, false);
            Toast.makeText(this, "No Favorites found!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_Button:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    closeDrawer();
                } else {
                    openDrawer();
                }
                break;
        }
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        getNavButton().setSelected(false);
    }

    private void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
        getNavButton().setSelected(true);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        closeDrawer();
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
