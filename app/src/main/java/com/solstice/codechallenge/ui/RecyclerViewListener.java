package com.solstice.codechallenge.ui;

import android.view.View;

/**
 * Created by fbucich on 1/14/18.
 */
public interface RecyclerViewListener {

    @FunctionalInterface
    interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }
}
