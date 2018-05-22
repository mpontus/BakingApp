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
                .repositoryModule(new RepositoryModule("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json"))
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
