package com.solstice.codechallenge.ui.contactList.recyclerView;

import com.solstice.codechallenge.data.model.Contact;

/**
 * Created by fbucich on 1/14/18.
 */

public class ContactListItem extends ListItem {
    private Contact contact;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public int getType() {
        return TYPE_CONTACT;
    }

}
