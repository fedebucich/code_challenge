package com.solstice.codechallenge.ui.contactDetail;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fbucich on 1/14/18.
 */
@Module
public class ContactDetailPresenterModule {
    private ContactDetailContract.View view;

    public ContactDetailPresenterModule(ContactDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public ContactDetailContract.View provideView() {
        return view;
    }
}
