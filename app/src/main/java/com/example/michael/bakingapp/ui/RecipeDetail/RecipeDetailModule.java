package com.example.michael.bakingapp.ui.RecipeDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.michael.bakingapp.data.Navigator;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.di.ActivityContext;
import com.google.gson.Gson;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.Single;

@Module
public abstract class RecipeDetailModule {

    @ContributesAndroidInjector
    abstract RecipeDetailFragment recipeDetailFragment();

    @Binds
    @ActivityContext
    abstract Context provideContext(RecipeDetailActivity activity);

    @Binds
    abstract RecipeDetailContract.View provideView(RecipeDetailActivity activity);

    @Binds
    abstract RecipeDetailContract.Presenter providePresenter(RecipeDetailPresenter presenter);

    @Binds
    abstract RecipeDetailContract.StepItemPresenterFactory provideStepPresenterFactory(RecipeDetailPresenter presenter);

    @Binds
    abstract RecipeDetailContract.IngredientItemPresenterFactory provideIngredientPresenterFactory(RecipeDetailPresenter presenter);

    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(@ActivityContext Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    static RecipeDetailContract.SelectStepStrategy provideSelectStepStrategy(RecipeDetailActivity activity,
                                                                             Navigator navigator) {
        if (activity.isMasterDetailLayout()) {
            return new SelectStepUpdateView(activity);
        }

        return new SelectStepLaunchActivity(navigator);
    }

    @Provides
    static Single<Recipe> provideRecipe(RecipeDetailActivity activity, Gson gson) {
        Intent intent = activity.getIntent();
        String serializedRecipe = intent.getStringExtra(RecipeDetailActivity.EXTRA_RECIPE);

        if (serializedRecipe == null) {
            throw new RuntimeException("Recipe detail activity may not be initialized without recipe");
        }

        return Single.just(gson.fromJson(serializedRecipe, Recipe.class));
    }
}
