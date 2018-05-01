package com.example.michael.bakingapp.ui.StepDetail;

import android.content.Intent;

import com.example.michael.bakingapp.data.schema.Step;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.gson.Gson;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.Single;

@Module
public abstract class StepDetailModule {
    @Binds
    abstract StepDetailContract.View provideView(StepDetailActivity activity);

    @Binds
    abstract StepDetailContract.Presenter providePresenter(StepDetailPresenter presenter);

    @Provides
    static Single<Step> provideStep(StepDetailActivity activity, Gson gson) {
        Intent intent = activity.getIntent();
        String serializedStep = intent.getStringExtra(StepDetailActivity.EXTRA_STEP);

        if (serializedStep == null) {
            throw new RuntimeException("Step must be provided to step detail activity");
        }

        return Single.just(gson.fromJson(serializedStep, Step.class));
    }

    @Provides
    static ExtractorMediaSource.Factory provideMediaSourceFactory(DataSource.Factory dataSourceFactory) {
        return new ExtractorMediaSource.Factory(dataSourceFactory);
    }

    @ContributesAndroidInjector
    abstract StepDetailFragment stepDetailFragment();
}
