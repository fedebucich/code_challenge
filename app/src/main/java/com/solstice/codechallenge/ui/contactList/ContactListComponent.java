package com.solstice.codechallenge.ui.contactList;

import com.solstice.codechallenge.data.ContactRepositoryComponent;
import com.solstice.codechallenge.ui.ActivityScope;
import com.solstice.codechallenge.util.schedulers.SchedulerModule;

import dagger.Component;

/**
 * Created by fbucich on 1/14/18.
 */
@ActivityScope
@Component(modules = {ContactListPresenterModule.class, SchedulerModule.class}, dependencies = {
        ContactRepositoryComponent.class
})

public interface ContactListComponent {
    void inject(ContactListActivity contactListActivity);
}
