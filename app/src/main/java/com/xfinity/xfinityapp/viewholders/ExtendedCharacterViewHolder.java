package com.xfinity.xfinityapp.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.models.Icon;
import com.xfinity.xfinityapp.models.RelatedTopic;
import com.xfinity.xfinityapp.util.Constants;

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

    public void invalidate(RelatedTopic data){
        super.invalidate(data);
        Icon icon = data.getIcon();
        if(icon == null){
            icon = data.getIconFromDB();
        }
        if(icon != null) {
            if (!icon.getURL().isEmpty()){
                Picasso.with(mContext).load(icon.getURL())
                        .placeholder(R.drawable.placeholder).into(imageView);
            }
        }
    }

}
