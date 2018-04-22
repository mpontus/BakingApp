package com.example.michael.bakingapp.ui.RecipeList;

import com.example.michael.bakingapp.data.Recipe;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class RecipeListPresenter implements RecipeListContract.Presenter,
        RecipeListContract.ItemPresenterFactory {

    private RecipeListContract.View view;
    private Single<Recipe[]> recipes;
    private CompositeDisposable compositeDisposable;
    private Provider<CompositeDisposable> compositeDisposableProvider;

    @Inject
    public RecipeListPresenter(RecipeListContract.View view,
                               Single<Recipe[]> recipes,
                               CompositeDisposable compositeDisposable,
                               Provider<CompositeDisposable> compositeDisposableProvider) {
        this.view = view;
        this.recipes = recipes;
        this.compositeDisposable = compositeDisposable;
        this.compositeDisposableProvider = compositeDisposableProvider;
    }

    @Override
    public void attach() {
        compositeDisposable.add(
                recipes.subscribe(recipes -> {
                    view.setRecipeCount(recipes.length);
                })
        );
    }

    @Override
    public void detach() {
        compositeDisposable.dispose();
    }

    @Override
    public RecipeListContract.ItemPresenter getItemPresenter(RecipeListContract.ItemView view, int position) {
        Single<Recipe> recipe = recipes.map(recipes -> recipes[position]);
        CompositeDisposable compositeDisposable = compositeDisposableProvider.get();

        return new RecipeItemPresenter(view, recipe, compositeDisposable);
    }

    static class RecipeItemPresenter implements RecipeListContract.ItemPresenter {
        private RecipeListContract.ItemView view;
        private Single<Recipe> recipe;
        private CompositeDisposable compositeDisposable;

        RecipeItemPresenter(RecipeListContract.ItemView view, Single<Recipe> recipe, CompositeDisposable compositeDisposable) {
            this.view = view;
            this.recipe = recipe;
            this.compositeDisposable = compositeDisposable;
        }

        @Override
        public void attach() {
            compositeDisposable.add(
                    recipe.subscribe(recipe -> {
                        view.setTitle(recipe.getName());
                    })
            );
        }

        @Override
        public void detach() {
            compositeDisposable.dispose();
        }
    }
}
