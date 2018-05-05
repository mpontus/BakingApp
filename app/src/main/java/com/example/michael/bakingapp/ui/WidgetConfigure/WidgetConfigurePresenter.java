package com.example.michael.bakingapp.ui.WidgetConfigure;

import com.example.michael.bakingapp.data.Preferences;
import com.example.michael.bakingapp.data.Repository;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.ui.Widget.WidgetUpdater;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;

public class WidgetConfigurePresenter implements WidgetConfigureContract.Presenter {

    private final WidgetConfigureContract.View view;
    private final WidgetUpdater widgetUpdater;
    private final int widgetId;
    private final Preferences preferences;
    private final Repository repository;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public WidgetConfigurePresenter(WidgetConfigureContract.View view,
                                    WidgetUpdater widgetUpdater, @Named("widgetId") int widgetId,
                                    Preferences preferences,
                                    Repository repository,
                                    CompositeDisposable compositeDisposable) {
        this.view = view;
        this.widgetUpdater = widgetUpdater;
        this.widgetId = widgetId;
        this.preferences = preferences;
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void attach() {
        compositeDisposable.addAll(
                repository.getRecipes()
                        .subscribe(view::setRecipes)
        );
    }

    @Override
    public void detach() {
        compositeDisposable.dispose();
    }

    @Override
    public void onSubmit(Recipe recipe) {
        preferences.setRecipeIdForWidget(widgetId, recipe.getId());
        widgetUpdater.updateWidget(widgetId);
        view.finishSuccess(widgetId);
    }
}
