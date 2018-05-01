package com.example.michael.bakingapp.ui.StepDetail;

import com.example.michael.bakingapp.data.schema.Step;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class StepDetailPresenter implements StepDetailContract.Presenter {
    private StepDetailContract.View view;
    private Step step;
    private CompositeDisposable compositeDisposable;

    @Inject
    public StepDetailPresenter(StepDetailContract.View view, Step step, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.step = step;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void attach() {
        view.setStep(step);
    }

    @Override
    public void detach() {
        compositeDisposable.dispose();
    }
}
