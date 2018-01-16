package com.solstice.codechallenge;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fbucich on 1/14/18.
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(Application context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }
}
