package com.xfinity.xfinityapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xfinity.xfinityapp.R;
import com.xfinity.xfinityapp.models.RelatedTopic;
import com.xfinity.xfinityapp.viewholders.CharacterViewHolder;

import java.util.List;

/**
 * Created by Ihsanulhaq on 3/12/2016.
 */
public class CharacterAdapter extends RecyclerView.Adapter{

    private final Context mContext;
    private final List<RelatedTopic> mItems;

    public CharacterAdapter(Context context, List<RelatedTopic> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.row_linear, viewGroup, false);
        return new CharacterViewHolder(mContext, v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((CharacterViewHolder)viewHolder).invalidate(mItems.get(i));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<RelatedTopic> items) {
        mItems.addAll(items);
    }
}