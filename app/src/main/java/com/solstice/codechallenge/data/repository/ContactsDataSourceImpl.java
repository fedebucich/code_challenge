package com.solstice.codechallenge.data.repository;

import com.solstice.codechallenge.data.api.ContactService;
import com.solstice.codechallenge.data.model.Contact;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by fbucich on 1/14/18.
 */
public class ContactsDataSourceImpl implements ContactsDataSource {
    private ContactService contactService;

    @Inject
    public ContactsDataSourceImpl(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public Flowable<List<Contact>> loadContacts() {
        return contactService.getContacts();
    }

}
