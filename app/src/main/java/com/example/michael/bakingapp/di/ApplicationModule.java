package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.data.RecipeRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
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
    RecipeRepository provideRecipeRepository(OkHttpClient client, Gson gson) {
        return new RecipeRepository(client, gson, RECIPE_LIST_URL);
    }
}
