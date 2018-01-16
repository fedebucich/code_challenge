package com.solstice.codechallenge.ui.contactDetail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;


import com.solstice.codechallenge.data.repository.ContactsRepository;


import javax.inject.Inject;


import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by fbucich on 1/14/18.
 */
public class ContactDetailPresenter implements ContactDetailContract.Presenter, LifecycleObserver {

    private ContactsRepository repository;

    private ContactDetailContract.View view;

    private CompositeDisposable disposeBag;

    @Inject
    public ContactDetailPresenter(ContactsRepository repository, ContactDetailContract.View view) {
        this.repository = repository;
        this.view = view;

        // Initialize this presenter as lifecycle-aware
        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }

        disposeBag = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {

    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }

    @Override
    public void toggleFavorite(String id) {
        view.updateFavoriteActionIcon(repository.toggleFavorite(id));
    }

    @Override
    public void loadContact(String id) {
        view.showContactDetail(repository.loadContact(id));
    }


}
