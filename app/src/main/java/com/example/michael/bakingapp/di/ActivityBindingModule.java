package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.ui.Widget.BakingAppWidget;
import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailActivity;
import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailModule;
import com.example.michael.bakingapp.ui.RecipeList.RecipeListActivity;
import com.example.michael.bakingapp.ui.RecipeList.RecipeListModule;
import com.example.michael.bakingapp.ui.StepDetail.StepDetailActivity;
import com.example.michael.bakingapp.ui.StepDetail.StepDetailModule;
import com.example.michael.bakingapp.ui.WidgetConfigure.WidgetConfigureActivity;
import com.example.michael.bakingapp.ui.WidgetConfigure.WidgetConfigureModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = RecipeListModule.class)
    abstract RecipeListActivity recipeListActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {RecipeDetailModule.class, PlayerModule.class})
    abstract RecipeDetailActivity recipeDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {StepDetailModule.class, PlayerModule.class})
    abstract StepDetailActivity stepDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = WidgetConfigureModule.class)
    abstract WidgetConfigureActivity widgetConfigureActivity();

    @ContributesAndroidInjector
    abstract BakingAppWidget bakingAppWidget();
}
