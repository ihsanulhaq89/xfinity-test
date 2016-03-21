package com.xfinity.xfinityapp.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.xfinity.xfinityapp.R;

/**
 * Created by Ihsanulhaq on 3/15/2016.
 */
public class SmallTextView extends android.widget.TextView {
    public SmallTextView(Context context) {
        super(context);
        init(context);
    }

    public SmallTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmallTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_file_path)));
    }


}
