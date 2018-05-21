package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.data.Connectivity;
import com.example.michael.bakingapp.data.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;

@Module
public class RepositoryModule {
    private String baseUrl;

    public RepositoryModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    @Provides
    @Singleton
    Repository provideRecipeRepository(OkHttpClient client,
                                       Gson gson,
                                       @Named("MAIN") Scheduler mainThreadScheduler,
                                       @Named("BACKGROUND") Scheduler backgroundThreadScheduler,
                                       Connectivity connectivity) {
        return new Repository(client, gson, mainThreadScheduler, backgroundThreadScheduler, baseUrl, connectivity);
    }

}
