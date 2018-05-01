package com.example.michael.bakingapp.ui.RecipeList;

import com.example.michael.bakingapp.data.Navigator;
import com.example.michael.bakingapp.data.schema.Recipe;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class RecipeListPresenter implements RecipeListContract.Presenter {

    private RecipeListContract.View view;
    private Single<Recipe[]> recipes;
    private CompositeDisposable compositeDisposable;
    private Navigator navigator;

    @Inject
    public RecipeListPresenter(RecipeListContract.View view,
                               Single<Recipe[]> recipes,
                               CompositeDisposable compositeDisposable,
                               Navigator navigator) {
        this.view = view;
        this.recipes = recipes;
        this.compositeDisposable = compositeDisposable;
        this.navigator = navigator;
    }

    @Override
    public void attach() {
        compositeDisposable.add(
                recipes.subscribe(recipes -> {
                    view.setRecipes(recipes);
                })
        );
    }

    @Override
    public void detach() {
        compositeDisposable.dispose();
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        navigator.launchRecipeDetailActivity(recipe);
    }
}
