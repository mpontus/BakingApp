package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.data.RecipeRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

@Module
public class ApplicationModule {
    private static final String RECIPE_LIST_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

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
    RecipeRepository provideRecipeRepository(OkHttpClient client,
                                             Gson gson,
                                             @Named("MAIN") Scheduler mainThreadScheduler,
                                             @Named("BACKGROUND") Scheduler backgroundThreadScheduler) {
        return new RecipeRepository(client, gson, mainThreadScheduler, backgroundThreadScheduler, RECIPE_LIST_URL);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Named("MAIN")
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named("BACKGROUND")
    Scheduler provideBackgroundThreadScheduler() {
        return Schedulers.io();
    }
}
