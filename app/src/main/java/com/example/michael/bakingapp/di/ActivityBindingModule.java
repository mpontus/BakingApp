package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailActivity;
import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailModule;
import com.example.michael.bakingapp.ui.RecipeList.RecipeListActivity;
import com.example.michael.bakingapp.ui.RecipeList.RecipeListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = RecipeListModule.class)
    abstract RecipeListActivity recipeListActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = RecipeDetailModule.class)
    abstract RecipeDetailActivity recipeDetailActivity();
}
