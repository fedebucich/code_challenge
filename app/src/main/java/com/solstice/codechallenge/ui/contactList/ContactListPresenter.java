package com.solstice.codechallenge.ui.contactList;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;


import com.solstice.codechallenge.data.model.Contact;
import com.solstice.codechallenge.data.repository.ContactsRepository;
import com.solstice.codechallenge.ui.contactList.recyclerView.ContactListHeader;
import com.solstice.codechallenge.ui.contactList.recyclerView.ContactListItem;
import com.solstice.codechallenge.ui.contactList.recyclerView.ListItem;
import com.solstice.codechallenge.util.schedulers.RunOn;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import java.util.Comparator;

import java.util.List;
import java.util.Map;

import java.util.TreeMap;


import javax.inject.Inject;


import io.reactivex.Scheduler;

import io.reactivex.disposables.CompositeDisposable;

import static com.solstice.codechallenge.util.schedulers.SchedulerType.IO;
import static com.solstice.codechallenge.util.schedulers.SchedulerType.UI;


/**
 * Created by fbucich on 1/14/18.
 */
public class ContactListPresenter implements ContactListContract.Presenter, LifecycleObserver {

    private ContactsRepository repository;
    private ContactListContract.View view;
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;
    private CompositeDisposable disposeBag;
    private Subscriber<List<Contact>> subscriber;
    private static final String FAVORITES_HEADER = "Favorite Contacts";
    private static final String OTHERS_HEADER = "Other Contacts";

    @Inject
    public ContactListPresenter(ContactsRepository repository, ContactListContract.View view,
                                @RunOn(IO) Scheduler ioScheduler, @RunOn(UI) Scheduler uiScheduler) {
        this.repository = repository;
        this.view = view;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;

        // Initialize this presenter as lifecycle-aware
        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }

        disposeBag = new CompositeDisposable();
        subscriber = getSubscriber();

    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        loadContacts(false);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void loadContacts(boolean forceRemote) {
        repository.loadContacts(forceRemote)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(subscriber);
    }

    private Subscriber<List<Contact>> getSubscriber() {
        return new Subscriber<List<Contact>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(List<Contact> contacts) {
                updateViewWithData(contacts);
            }

            @Override
            public void onError(Throwable t) {
                handleError(t);
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private void updateViewWithData(List<Contact> list) {
        view.stopLoadingIndicator();
        if (list != null && !list.isEmpty()) {
            List<ListItem> consolidatedList = groupAndSortContacts(list);
            view.showContacts(consolidatedList);
        } else {
            view.showNoDataMessage();
        }
    }

    /**
     * Groups the contacts by favorite status, and sorts every group
     */
    private List<ListItem> groupAndSortContacts(List<Contact> list) {
        Map<String, List<Contact>> groupedContacts = groupDataByFavoriteStatus(list);
        return sortByName(groupedContacts);
    }


    /**
     * Groups the contacts by favorite status
     */
    private Map<String, List<Contact>> groupDataByFavoriteStatus(List<Contact> contacts) {

        Map<String, List<Contact>> groupedHashMap = new TreeMap<>();

        for (Contact contact : contacts) {

            String hashMapKey = contact.isFavorite() ? FAVORITES_HEADER : OTHERS_HEADER;

            if (groupedHashMap.containsKey(hashMapKey)) {
                groupedHashMap.get(hashMapKey).add(contact);
            } else {
                List<Contact> list = new ArrayList<>();
                list.add(contact);
                groupedHashMap.put(hashMapKey, list);
            }
        }

        return groupedHashMap;
    }

    /**
     * Consolidates de groups into a single sorted List for the adapter
     */
    private List<ListItem> sortByName(Map<String, List<Contact>> groupedContacts) {
        List<ListItem> consolidatedList = new ArrayList<>();

        for (String header : groupedContacts.keySet()) {
            //Create header item
            List<ContactListItem> contactListItems = new ArrayList<>();
            consolidatedList.add(getHeaderListItem(header));

            //Create every contact item inside the group
            for (Contact contact : groupedContacts.get(header)) {
                contactListItems.add(getContactListItem(contact));
            }

            //Sort by name
            sortGroup(contactListItems);
            consolidatedList.addAll(contactListItems);
        }

        return consolidatedList;
    }

    /**
     * Sorts the received group of contacts
     */
    private void sortGroup(List<ContactListItem> contactListItems) {
        contactListItems.sort(new Comparator<ContactListItem>() {
            @Override
            public int compare(ContactListItem contactListItem, ContactListItem t1) {
                return contactListItem.getContact().getName().compareTo(t1.getContact().getName());
            }
        });
    }

    private ContactListItem getContactListItem(Contact contact) {
        ContactListItem contactListItem = new ContactListItem();
        contactListItem.setContact(contact);
        return contactListItem;
    }

    private ContactListHeader getHeaderListItem(String headerTitle) {
        ContactListHeader header = new ContactListHeader();
        header.setHeader(headerTitle);
        return header;
    }

    private void handleError(Throwable error) {
        view.stopLoadingIndicator();
        view.showErrorMessage(error.getLocalizedMessage());
    }
}
