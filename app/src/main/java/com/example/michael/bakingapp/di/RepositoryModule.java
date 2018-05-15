package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.data.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;

@Module
public class RepositoryModule {
    private static final String RECIPE_LIST_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private String baseUrl;

    public RepositoryModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    @Provides
    Repository provideRecipeRepository(OkHttpClient client,
                                       Gson gson,
                                       @Named("MAIN") Scheduler mainThreadScheduler,
                                       @Named("BACKGROUND") Scheduler backgroundThreadScheduler) {
        return new Repository(client, gson, mainThreadScheduler, backgroundThreadScheduler, RECIPE_LIST_URL);
    }

}
