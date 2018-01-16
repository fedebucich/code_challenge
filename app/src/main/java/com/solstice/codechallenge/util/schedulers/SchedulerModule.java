package com.solstice.codechallenge.util.schedulers;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.solstice.codechallenge.util.schedulers.SchedulerType.IO;
import static com.solstice.codechallenge.util.schedulers.SchedulerType.UI;

/**
 * Created by fbucich on 1/14/18.
 */
@Module
public class SchedulerModule {

    @Provides
    @RunOn(IO)
    Scheduler provideIo() {
        return Schedulers.io();
    }

    @Provides
    @RunOn(UI)
    Scheduler provideUi() {
        return AndroidSchedulers.mainThread();
    }
}
