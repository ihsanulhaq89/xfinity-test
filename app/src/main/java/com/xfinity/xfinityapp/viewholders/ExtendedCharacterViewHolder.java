package com.xfinity.xfinityapp.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.models.Icon;
import com.xfinity.xfinityapp.models.RelatedTopic;

/**
 * Created by Ihsanulhaq on 3/12/2016.
 */
public class ExtendedCharacterViewHolder extends CharacterViewHolder {

    private final Context mContext;
    private ImageView imageView;

    public ExtendedCharacterViewHolder(Context context, View itemView) {
        super(context, itemView);
        this.mContext = context;
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void invalidate(RelatedTopic data, int index) {
        super.invalidate(data, index);
        Icon icon = data.getIcon();
        if (icon == null) {
            icon = data.getIconFromDB();
        }
        if (icon != null) {
            if (!icon.getURL().isEmpty()) {
                Picasso.with(mContext).load(icon.getURL())
                        .placeholder(R.drawable.placeholder).into(imageView);
            }
        }
    }

}
