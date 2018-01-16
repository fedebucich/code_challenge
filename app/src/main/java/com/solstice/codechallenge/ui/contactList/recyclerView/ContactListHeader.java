package com.solstice.codechallenge.ui.contactList.recyclerView;


/**
 * Created by fbucich on 1/14/18.
 */
public class ContactListHeader extends ListItem {
    private String header;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

}
