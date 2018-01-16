package com.solstice.codechallenge.data;

import com.solstice.codechallenge.data.repository.ContactsDataSource;
import com.solstice.codechallenge.data.repository.ContactsDataSourceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fbucich on 1/14/18.
 */
@Module
public class ContactRepositoryModule {

    @Provides
    @Singleton
    public ContactsDataSource provideDataSource(ContactsDataSourceImpl contactsDataSource) {
        return contactsDataSource;
    }

}
