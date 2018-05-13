package com.example.michael.bakingapp.ui.StepDetail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;
import com.example.michael.bakingapp.di.ActivityContext;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.gson.Gson;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StepDetailModule {
    @Binds
    abstract StepDetailContract.View provideView(StepDetailActivity activity);

    @Binds
    abstract StepDetailContract.Presenter providePresenter(StepDetailPresenter presenter);

    @Binds
    @ActivityContext
    abstract Context provideContext(StepDetailActivity activity);

    @Provides
    static Recipe provideRecipe(StepDetailActivity activity, Gson gson) {
        Intent intent = activity.getIntent();
        String serializedRecipe = intent.getStringExtra(StepDetailActivity.EXTRA_RECIPE);

        if (serializedRecipe == null) {
            throw new RuntimeException("Recipe must be provided to step detail activity");
        }

        return gson.fromJson(serializedRecipe, Recipe.class);
    }


    @Provides
    static Step provideStep(StepDetailActivity activity, Gson gson) {
        Intent intent = activity.getIntent();
        String serializedStep = intent.getStringExtra(StepDetailActivity.EXTRA_STEP);

        if (serializedStep == null) {
            throw new RuntimeException("Step must be provided to step detail activity");
        }

        return gson.fromJson(serializedStep, Step.class);
    }

    static private int getStepIndex(Recipe recipe, Step step) {
        Step[] steps = recipe.getSteps();
        for (int i = 0; i < steps.length; ++i) {
            if (steps[i].getId() == step.getId()) {
                return i;
            }
        }

        return -1;
    }

    @Nullable
    @Named("NEXT")
    @Provides
    static Step provideNextStep(Recipe recipe, Step step) {
        Step[] steps = recipe.getSteps();
        int currentIndex = getStepIndex(recipe, step);

        if (currentIndex == steps.length) {
            return null;
        }

        return steps[currentIndex + 1];
    }

    @Nullable
    @Named("PREV")
    @Provides
    static Step providePreviousStep(Recipe recipe, Step step) {
        Step[] steps = recipe.getSteps();
        int currentIndex = getStepIndex(recipe, step);

        if (currentIndex <= 0) {
            return null;
        }

        return steps[currentIndex - 1];
    }

    @Provides
    static ExtractorMediaSource.Factory provideMediaSourceFactory(DataSource.Factory dataSourceFactory) {
        return new ExtractorMediaSource.Factory(dataSourceFactory);
    }

    @ContributesAndroidInjector
    abstract StepDetailFragment stepDetailFragment();
}
