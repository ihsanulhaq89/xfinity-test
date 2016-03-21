package com.xfinity.xfinityapp.activities;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.customviews.TitleTextView;

/**
 * Created by Ihsanulhaq on 3/15/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private TitleTextView title;
    private TitleTextView secondaryTitle;
    private ImageButton navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        setTransitions();
    }


    private void setTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideRight = new Slide(Gravity.RIGHT);
            slideRight.setDuration(200);
            Fade fade = new Fade();
            fade.setDuration(100);
            getWindow().setEnterTransition(slideRight);
            getWindow().setExitTransition(fade);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setActionBar();
    }

    public void showSecondaryTitle() {
        secondaryTitle.setVisibility(View.VISIBLE);
    }

    public void showNav() {
        navButton.setVisibility(View.VISIBLE);
    }

    private void setActionBar() {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.app_bar_color))); // set your desired color
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.actionbar, null);
        title = ((TitleTextView) v.findViewById(R.id.title));
        secondaryTitle = ((TitleTextView) v.findViewById(R.id.secondaryTitle));
        navButton = ((ImageButton) v.findViewById(R.id.nav_Button));
        title.setText(this.getTitle());

        this.getSupportActionBar().setCustomView(v);
    }

    public ImageButton getNavButton() {
        return navButton;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
