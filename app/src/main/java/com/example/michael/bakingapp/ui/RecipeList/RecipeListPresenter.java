package com.example.michael.bakingapp.ui.RecipeList;

import com.example.michael.bakingapp.data.Navigator;
import com.example.michael.bakingapp.data.schema.Recipe;

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
    private Navigator navigator;

    @Inject
    public RecipeListPresenter(RecipeListContract.View view,
                               Single<Recipe[]> recipes,
                               CompositeDisposable compositeDisposable,
                               Provider<CompositeDisposable> compositeDisposableProvider, Navigator navigator) {
        this.view = view;
        this.recipes = recipes;
        this.compositeDisposable = compositeDisposable;
        this.compositeDisposableProvider = compositeDisposableProvider;
        this.navigator = navigator;
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

        return new RecipeItemPresenter(view, recipe, compositeDisposable, navigator);
    }

    static class RecipeItemPresenter implements RecipeListContract.ItemPresenter {
        private RecipeListContract.ItemView view;
        private Single<Recipe> recipe;
        private CompositeDisposable compositeDisposable;
        private Navigator navigator;

        RecipeItemPresenter(RecipeListContract.ItemView view,
                            Single<Recipe> recipe,
                            CompositeDisposable compositeDisposable,
                            Navigator navigator) {
            this.view = view;
            this.recipe = recipe;
            this.compositeDisposable = compositeDisposable;
            this.navigator = navigator;
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

        @Override
        public void onClick() {
            compositeDisposable.add(
                    recipe.subscribe(navigator::launchRecipeDetailActivity)
            );
        }
    }
}
