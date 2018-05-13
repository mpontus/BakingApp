package com.example.michael.bakingapp.ui.RecipeDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.di.ActivityContext;
import com.example.michael.bakingapp.ui.StepDetail.StepDetailFragment;
import com.example.michael.bakingapp.ui.utils.QuantityFormatter;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.gson.Gson;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecipeDetailModule {

    @ContributesAndroidInjector
    abstract RecipeDetailFragment recipeDetailFragment();

    @ContributesAndroidInjector
    abstract StepDetailFragment stepDetailFragment();

    @Provides
    static ExtractorMediaSource.Factory provideMediaSourceFactory(DataSource.Factory dataSourceFactory) {
        return new ExtractorMediaSource.Factory(dataSourceFactory);
    }

    @Binds
    @ActivityContext
    abstract Context provideContext(RecipeDetailActivity activity);

    @Binds
    abstract RecipeDetailContract.View provideView(RecipeDetailActivity activity);

    @Binds
    abstract RecipeDetailContract.Presenter providePresenter(RecipeDetailPresenter presenter);

    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(@ActivityContext Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    static IngredientListAdapter provideIngredientsAdapter(QuantityFormatter quantityFormatter) {
        return new IngredientListAdapter(quantityFormatter);
    }

    @Provides
    static StepsAdapter provideStepsAdapter(RecipeDetailActivity activity) {
        return new StepsAdapter(activity);
    }

    @Provides
    static Recipe provideRecipe(RecipeDetailActivity activity, Gson gson) {
        Intent intent = activity.getIntent();
        String serializedRecipe = intent.getStringExtra(RecipeDetailActivity.EXTRA_RECIPE);

        if (serializedRecipe == null) {
            throw new RuntimeException("Recipe detail activity may not be initialized without recipe");
        }

        return gson.fromJson(serializedRecipe, Recipe.class);
    }
}
