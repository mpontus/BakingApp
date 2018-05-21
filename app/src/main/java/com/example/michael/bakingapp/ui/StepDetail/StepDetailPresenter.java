package com.example.michael.bakingapp.ui.StepDetail;

import android.support.annotation.Nullable;

import com.example.michael.bakingapp.data.Navigator;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;

public class StepDetailPresenter implements StepDetailContract.Presenter {
    private StepDetailContract.View view;
    private Recipe recipe;
    private Step step;
    private Step prevStep;
    private Step nextStep;
    private CompositeDisposable compositeDisposable;
    private SimpleExoPlayer exoPlayer;
    private Navigator navigator;

    @Inject
    public StepDetailPresenter(StepDetailContract.View view,
                               Recipe recipe,
                               Step step,
                               @Nullable @Named("PREV") Step prevStep,
                               @Nullable @Named("NEXT") Step nextStep,
                               CompositeDisposable compositeDisposable,
                               SimpleExoPlayer exoPlayer,
                               Navigator navigator) {
        this.view = view;
        this.recipe = recipe;
        this.step = step;
        this.prevStep = prevStep;
        this.nextStep = nextStep;
        this.compositeDisposable = compositeDisposable;
        this.exoPlayer = exoPlayer;
        this.navigator = navigator;
    }

    @Override
    public void attach() {
        view.setStep(step);

        view.setNextStepHidden(nextStep == null);
        view.setPrevStepHidden(prevStep == null);
    }

    @Override
    public void detach() {
        compositeDisposable.dispose();
        exoPlayer.stop();
    }

    @Override
    public void onPrevClick() {
        if (prevStep == null) {
            return;
        }

        navigator.launchStepDetailActivity(recipe, prevStep);
    }

    @Override
    public void onNextClick() {
        if (nextStep == null) {
            return;
        }

        navigator.launchStepDetailActivity(recipe, nextStep);
    }
}
