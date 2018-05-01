package com.example.michael.bakingapp.ui.RecipeDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Recipe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class RecipeDetailFragment extends DaggerFragment {
    @Inject
    IngredientListAdapter ingredientsAdapter;

    @Inject
    RecyclerView.LayoutManager ingredientsLayoutManager;

    @Inject
    StepsAdapter stepsAdapter;

    @Inject
    RecyclerView.LayoutManager stepsLayoutManager;

    @BindView(R.id.ingredients)
    RecyclerView ingredientsView;

    @BindView(R.id.steps)
    RecyclerView stepsView;

    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail, container, false);

        ButterKnife.bind(this, view);

        ingredientsView.setNestedScrollingEnabled(false);
        ingredientsView.setAdapter(ingredientsAdapter);
        ingredientsView.setLayoutManager(ingredientsLayoutManager);

        stepsView.setNestedScrollingEnabled(false);
        stepsView.setAdapter(stepsAdapter);
        stepsView.setLayoutManager(stepsLayoutManager);

        return view;
    }

    public void setRecipe(Recipe recipe) {
        ingredientsAdapter.setIngredients(recipe.getIngredients());
        stepsAdapter.setSteps(recipe.getSteps());
    }
}
