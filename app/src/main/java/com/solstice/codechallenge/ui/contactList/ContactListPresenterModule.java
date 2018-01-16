package com.solstice.codechallenge.ui.contactList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fbucich on 1/14/18.
 */
@Module
public class ContactListPresenterModule {
    private ContactListContract.View view;

    public ContactListPresenterModule(ContactListContract.View view) {
        this.view = view;
    }

    @Provides
    public ContactListContract.View provideView() {
        return view;
    }
}
