package com.xfinity.xfinityapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.fragments.DetailFragment;
import com.xfinity.xfinityapp.models.RelatedTopic;
import com.xfinity.xfinityapp.util.BundleConstants;

public class DetailActivity extends BaseActivity implements DetailFragment.OnFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        overridePendingTransition(R.anim.push_out_left, R.anim.push_out_right);

        Bundle bundle = getIntent().getExtras();
        RelatedTopic data = (RelatedTopic) bundle.getSerializable(BundleConstants.B_DATA);
        Long id = bundle.getLong(BundleConstants.B_ID);
        int index = bundle.getInt(BundleConstants.B_INDEX);
        data.setId(id);
        setTitle(data.getTitle());

        DetailFragment fragment = ((DetailFragment) getFragmentManager()
                .findFragmentById(R.id.details_frag));

        fragment.update(data, index);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {
            this.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetailFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
