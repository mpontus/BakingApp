package com.example.michael.bakingapp.ui.RecipeList;

import android.os.Bundle;
import android.util.Log;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.RecipeRepository;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeListActivity extends DaggerAppCompatActivity {

    @Inject
    RecipeRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        repo.getRecipes().subscribe(recipes -> {
            Log.v("RecipeListActivity", "" + recipes.length);
        });
    }
}
