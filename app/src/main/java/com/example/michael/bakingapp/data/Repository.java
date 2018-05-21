package com.example.michael.bakingapp.data;

import android.support.annotation.NonNull;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Repository {
    private OkHttpClient client;
    private Gson gson;
    private Scheduler mainThreadScheduler;
    private Scheduler backgroundThreadScheduler;
    private String recipesUrl;
    private Connectivity connectivity;

    public Repository(OkHttpClient client,
                      Gson gson,
                      @Named("MAIN") Scheduler mainThreadScheduler,
                      @Named("BACKGROUND") Scheduler backgroundThreadScheduler,
                      String recipesUrl,
                      Connectivity connectivity) {
        this.client = client;
        this.gson = gson;
        this.mainThreadScheduler = mainThreadScheduler;
        this.backgroundThreadScheduler = backgroundThreadScheduler;
        this.recipesUrl = recipesUrl;
        this.connectivity = connectivity;
    }

    public Single<Recipe[]> getRecipesInDefaultThread() {
        return connectivity.isOnline()
                .filter(isOnline -> isOnline)
                .singleOrError()
                .to(isOnline -> fetchRecipes())
                .cache();
    }

    public Single<Recipe[]> getRecipes() {
        return getRecipesInDefaultThread()
                .subscribeOn(backgroundThreadScheduler)
                .observeOn(mainThreadScheduler);
    }

    public Single<Recipe> getRecipeById(long id) {
        return getRecipes()
                .toObservable()
                .flatMap(Observable::fromArray)
                .filter(recipe -> recipe.getId() == id)
                .singleOrError();
    }

    public Recipe getRecipeByIdSync(long id) {
        return getRecipesInDefaultThread()
                .toObservable()
                .flatMap(Observable::fromArray)
                .filter(recipe -> recipe.getId() == id)
                .blockingFirst();
    }

    private Single<Recipe[]> fetchRecipes() {
        return Single.create(emitter -> {
            Request request = new Request.Builder()
                    .url(recipesUrl)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    emitter.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    ResponseBody responseBody = response.body();

                    if (responseBody == null) {
                        return;
                    }

                    Reader reader = responseBody.charStream();

                    emitter.onSuccess(gson.fromJson(reader, Recipe[].class));
                }
            });
        });
    }
}
