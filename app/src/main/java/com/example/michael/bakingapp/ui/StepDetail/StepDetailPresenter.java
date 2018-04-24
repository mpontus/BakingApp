package com.example.michael.bakingapp.ui.StepDetail;

import com.example.michael.bakingapp.data.schema.Step;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class StepDetailPresenter implements StepDetailContract.Presenter {
    private StepDetailContract.View view;
    private Single<Step> step;
    private CompositeDisposable compositeDisposable;

    @Inject
    public StepDetailPresenter(StepDetailContract.View view, Single<Step> step, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.step = step;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void attach() {
        compositeDisposable.add(
                step.subscribe(step -> {
                    view.setDescription(step.getDescription());
                    view.setVideoUrl(step.getVideoURL());
                })
        );
    }

    @Override
    public void detach() {
        compositeDisposable.dispose();
    }
}
