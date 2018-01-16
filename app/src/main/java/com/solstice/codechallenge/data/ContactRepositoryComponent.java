package com.solstice.codechallenge.data;

import com.solstice.codechallenge.AppModule;
import com.solstice.codechallenge.data.repository.ContactsRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fbucich on 1/14/18.
 */
@Singleton
@Component(modules = {ContactRepositoryModule.class, AppModule.class, ApiServiceModule.class})
public interface ContactRepositoryComponent {
    ContactsRepository provideContactsRepository();
}
