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
import com.xfinity.xfinityapp.viewholders.ExtendedCharacterViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ihsanulhaq on 3/12/2016.
 */
public class CharacterAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private List<RelatedTopic> mItems;
    private boolean grid;

    public CharacterAdapter(Context context, List<RelatedTopic> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (grid) {
            View v = LayoutInflater.from(mContext)
                    .inflate(R.layout.row_grid, viewGroup, false);
            return new ExtendedCharacterViewHolder(mContext, v);

        } else {
            View v = LayoutInflater.from(mContext)
                    .inflate(R.layout.row_linear, viewGroup, false);
            return new CharacterViewHolder(mContext, v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((CharacterViewHolder) viewHolder).invalidate(mItems.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<RelatedTopic> items) {
        mItems = items;
    }

    public List<RelatedTopic> getItems() {
        return mItems;
    }

    public void setGrid(boolean grid) {
        this.grid = grid;
    }
}
