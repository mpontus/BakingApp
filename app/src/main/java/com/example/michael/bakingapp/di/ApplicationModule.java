package com.example.michael.bakingapp.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@Module
abstract class ApplicationModule {
    @Binds
    abstract Context provideContext(Application application);

    @Provides
    static SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Named("MAIN")
    static Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named("BACKGROUND")
    static Scheduler provideBackgroundThreadScheduler() {
        return Schedulers.io();
    }
}
