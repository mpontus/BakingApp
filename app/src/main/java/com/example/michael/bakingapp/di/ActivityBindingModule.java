package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.ui.RecipeList.RecipeListActivity;
import com.example.michael.bakingapp.ui.RecipeList.RecipeListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @AcitivtyScoped
    @ContributesAndroidInjector(modules = RecipeListModule.class)
    abstract RecipeListActivity recipeListActivity();
}
