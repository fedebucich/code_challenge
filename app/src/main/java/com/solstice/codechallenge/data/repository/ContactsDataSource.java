package com.solstice.codechallenge.data.repository;


import com.solstice.codechallenge.data.model.Contact;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by fbucich on 1/14/18.
 */
public interface ContactsDataSource {

    Flowable<List<Contact>> loadContacts();

}
