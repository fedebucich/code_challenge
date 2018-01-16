package com.solstice.codechallenge.data.repository;


import android.support.annotation.VisibleForTesting;

import com.solstice.codechallenge.data.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by fbucich on 1/14/18.
 */
public class ContactsRepository {

    private ContactsDataSourceImpl contactsDataSource;
    private Consumer<List<Contact>> onNext;

    @VisibleForTesting
    public static List<Contact> contactsCache;

    @Inject
    public ContactsRepository(ContactsDataSourceImpl contactsDataSource) {
        this.contactsDataSource = contactsDataSource;
        this.onNext = getOnNext();

        if (contactsCache == null) {
            contactsCache = new ArrayList<>();
        }
    }

    public Flowable<List<Contact>> loadContacts(boolean forceRemote) {
        return contactsCache.size() > 0 && !forceRemote
                ? Flowable.just(contactsCache)
                : contactsDataSource.loadContacts().doOnNext(this.onNext);
    }

    private Consumer<List<Contact>> getOnNext() {
        return new Consumer<List<Contact>>() {
            @Override
            public void accept(List<Contact> contacts) throws Exception {
                contactsCache = new ArrayList<>();
                contactsCache.addAll(contacts);
            }
        };
    }

    public Contact loadContact(String id) {
        for (Contact contact : contactsCache) {
            if (Objects.equals(contact.getId(), id)) {
                return contact;
            }
        }
        return null;
    }

    public Contact toggleFavorite(String id) {
        Contact c = loadContact(id);
        c.setFavorite(!c.isFavorite());
        return c;
    }

}
