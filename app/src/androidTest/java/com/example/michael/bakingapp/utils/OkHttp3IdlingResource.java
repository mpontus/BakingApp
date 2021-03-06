package com.example.michael.bakingapp.utils;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingResource;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

// Source: https://github.com/JakeWharton/okhttp-idling-resource

/**
 * An {@link IdlingResource) for {@link OkHttpClient}.
 */
public final class OkHttp3IdlingResource implements IdlingResource {
    /**
     * Create a new {@link IdlingResource} from {@code client} as {@code name}. You must register
     * this instance using {@code Espresso.registerIdlingResources}.
     */
    @CheckResult
    @NonNull
    @SuppressWarnings("ConstantConditions") // Extra guards as a library.
    public static OkHttp3IdlingResource create(@NonNull String name, @NonNull OkHttpClient client) {
        if (name == null) throw new NullPointerException("name == null");
        if (client == null) throw new NullPointerException("client == null");
        return new OkHttp3IdlingResource(name, client.dispatcher());
    }

    private final String name;
    private final Dispatcher dispatcher;
    volatile ResourceCallback callback;

    private OkHttp3IdlingResource(String name, Dispatcher dispatcher) {
        this.name = name;
        this.dispatcher = dispatcher;
        dispatcher.setIdleCallback(new Runnable() {
            @Override
            public void run() {
                ResourceCallback callback = OkHttp3IdlingResource.this.callback;
                if (callback != null) {
                    callback.onTransitionToIdle();
                }
            }
        });
    }

    @Override
    public String getName() {
        return name;
    }

    // Source: https://github.com/JakeWharton/okhttp-idling-resource/issues/10
    @Override
    public boolean isIdleNow() {
        boolean idle = (dispatcher.runningCallsCount() == 0);
        if (idle && callback != null) callback.onTransitionToIdle();
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}
