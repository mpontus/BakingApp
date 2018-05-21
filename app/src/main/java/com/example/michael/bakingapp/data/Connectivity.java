package com.example.michael.bakingapp.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Connectivity {
    private Context context;

    @Inject
    public Connectivity(Context context) {
        this.context = context;
    }

    public Observable<Boolean> isOnline() {
        return getBroadcasts(new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION))
                .map(intent -> !intent.getBooleanExtra(android.net.ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                        false));
    }

    private Observable<Intent> getBroadcasts(IntentFilter intentFilter) {
        return Observable.create(emitter -> {
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    emitter.onNext(intent);
                }
            };

            emitter.setCancellable(() -> context.unregisterReceiver(receiver));

            context.registerReceiver(receiver, intentFilter);
        });
    }
}
