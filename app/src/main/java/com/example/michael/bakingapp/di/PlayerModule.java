package com.example.michael.bakingapp.di;

import android.content.Context;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayerModule {

    private static final String USER_AGENT = "bakingApp";

    @Provides
    DefaultBandwidthMeter provideDefaultBandwidthMeter() {
        return new DefaultBandwidthMeter();
    }

    @Provides
    BandwidthMeter provideBandwidthMeter(DefaultBandwidthMeter defaultBandwidthMeter) {
        return defaultBandwidthMeter;
    }


    @Provides
    DataSource.Factory provideDataSourceFactory(Context context, DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, USER_AGENT),
                bandwidthMeter);
    }

    @Provides
    TrackSelector provideTrackSelector(BandwidthMeter bandwidthMeter) {
        return new DefaultTrackSelector(bandwidthMeter);
    }

    @Singleton
    @Provides
    SimpleExoPlayer provideSimpleExoPlayer(Context context, TrackSelector trackSelector) {
        return ExoPlayerFactory.newSimpleInstance(context, trackSelector);
    }
}
