package com.xfinity.xfinityapp.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.models.RelatedTopic;
import com.xfinity.xfinityapp.util.BundleConstants;
import com.xfinity.xfinityapp.util.EventConstants;

/**
 * Created by Ihsanulhaq on 3/12/2016.
 */
public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Context mContext;
    private TextView itemView;
    private RelatedTopic data;
    private int index;

    public CharacterViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.itemView = (TextView) itemView.findViewById(R.id.title);
        itemView.setOnClickListener(this);
    }

    public void invalidate(RelatedTopic data, int index) {
        setData(data);
        this.index = index;
        itemView.setText(data.getTitle());
    }

    @Override
    public void onClick(View view) {
        sendMessage();
    }

    public RelatedTopic getData() {
        return data;
    }

    public void setData(RelatedTopic data) {
        this.data = data;
    }

    private void sendMessage() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleConstants.B_DATA, getData());
        bundle.putLong(BundleConstants.B_ID, getData().getId());
        bundle.putInt(BundleConstants.B_INDEX, index);
        Intent intent = new Intent(EventConstants.BROADCAST_SELECTION);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }
}
