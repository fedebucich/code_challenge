package com.solstice.codechallenge;

import android.app.Application;

import com.solstice.codechallenge.data.ContactRepositoryComponent;
import com.solstice.codechallenge.data.DaggerContactRepositoryComponent;

/**
 * Created by fbucich on 1/14/18.
 */
public class AndroidApplication extends Application {

    private ContactRepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencies();
    }

    private void initializeDependencies() {
        repositoryComponent = DaggerContactRepositoryComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ContactRepositoryComponent getContactRepositoryComponent() {
        return repositoryComponent;
    }
}
