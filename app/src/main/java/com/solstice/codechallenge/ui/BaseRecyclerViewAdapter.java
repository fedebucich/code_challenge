package com.solstice.codechallenge.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.reactivex.annotations.NonNull;

/**
 * Created by fbucich on 1/14/18.
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerViewListener.OnItemClickListener itemClickListener;

    public void setOnItemClickListener(
            @NonNull RecyclerViewListener.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (itemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnItemClick(view, i);
                }
            });
        }
    }
}
