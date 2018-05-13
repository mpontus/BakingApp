package com.example.michael.bakingapp.ui.StepDetail;

import android.content.Intent;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;
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

    @Provides
    static Recipe provideRecipe(StepDetailActivity activity, Gson gson) {
        Intent intent = activity.getIntent();
        String serializedRecipe = intent.getStringExtra(StepDetailActivity.EXTRA_RECIPE);

        if (serializedRecipe == null) {
            throw new RuntimeException("Recipe must be provided to step detail activity");
        }

        return gson.fromJson(serializedRecipe, Recipe.class);
    }

    @Named("STEP_INDEX")
    @Provides
    static Integer provideStepIndex(StepDetailActivity activity, Gson gson) {
        Intent intent = activity.getIntent();

        return intent.getIntExtra(StepDetailActivity.EXTRA_STEP_INDEX, -1);
    }


    @Provides
    static Step provideCurrentStep(Recipe recipe, @Named("STEP_INDEX") Integer stepIndex) {
        Step[] steps = recipe.getSteps();

        if (stepIndex < 0 || stepIndex > steps.length) {
            throw new RuntimeException("Step detail activity must be initialized with a step");
        }

        return steps[stepIndex];
    }

    @Named("NEXT")
    @Provides
    static Step provideNextStep(Recipe recipe, @Named("STEP_INDEX") Integer stepIndex) {
        Step[] steps = recipe.getSteps();

        if (stepIndex < 0 || stepIndex > steps.length - 1) {
            return null;
        }

        return steps[stepIndex + 1];
    }

    @Named("PREV")
    @Provides
    static Step providePreviousStep(Recipe recipe, @Named("STEP_INDEX") Integer stepIndex) {
        Step[] steps = recipe.getSteps();

        if (stepIndex <= 0 || stepIndex > steps.length) {
            return null;
        }

        return steps[stepIndex - 1];
    }

    @Provides
    static ExtractorMediaSource.Factory provideMediaSourceFactory(DataSource.Factory dataSourceFactory) {
        return new ExtractorMediaSource.Factory(dataSourceFactory);
    }

    @ContributesAndroidInjector
    abstract StepDetailFragment stepDetailFragment();
}
