package com.example.michael.bakingapp;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;

import com.example.michael.bakingapp.di.ApplicationModule;
import com.example.michael.bakingapp.di.DaggerAppComponent;
import com.example.michael.bakingapp.di.RepositoryModule;
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasServiceInjector;
import okhttp3.OkHttpClient;

public class BakingAppApplication extends Application implements HasActivityInjector, HasBroadcastReceiverInjector, HasServiceInjector {
    @Inject
    public OkHttpClient okHttpClient;

    @Inject
    public Gson gson;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidActivityInjector;

    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> dispatchingAndroidBroadcastReceiverInjector;

    @Inject
    DispatchingAndroidInjector<Service> dispatchingAndroidServiceInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .repositoryModule(new RepositoryModule("https://gist.githubusercontent.com/mpontus/8ea265da06938f52d15d241cc8d33ecb/raw/aa6f71091433ac6c7d40202c7567f1f2d061dd18/gistfile1.txt"))
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidActivityInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return dispatchingAndroidBroadcastReceiverInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingAndroidServiceInjector;
    }
}
