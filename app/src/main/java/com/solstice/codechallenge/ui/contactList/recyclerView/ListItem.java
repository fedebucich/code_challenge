package com.solstice.codechallenge.ui.contactList.recyclerView;

/**
 * Created by fbucich on 1/14/18.
 */
public abstract class ListItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CONTACT = 1;

    abstract public int getType();
}
