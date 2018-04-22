package com.example.michael.bakingapp.data;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

import io.reactivex.Single;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RecipeRepository {
    private OkHttpClient client;
    private Gson gson;
    private String recipesUrl;

    public RecipeRepository(OkHttpClient client, Gson gson, String recipesUrl) {
        this.client = client;
        this.gson = gson;
        this.recipesUrl = recipesUrl;
    }

    public Single<Recipe[]> getRecipes() {
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
                        emitter.onSuccess(null);

                        return;
                    }

                    Reader reader = responseBody.charStream();

                    emitter.onSuccess(gson.fromJson(reader, Recipe[].class));
                }
            });
        });
    }
}
