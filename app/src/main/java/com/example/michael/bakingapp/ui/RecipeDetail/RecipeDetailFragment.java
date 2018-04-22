package com.example.michael.bakingapp.ui.RecipeDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michael.bakingapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class RecipeDetailFragment extends DaggerFragment {
    @Inject
    IngredientListAdapter ingredientListAdapter;

    @Inject
    RecyclerView.LayoutManager ingredientListLayoutManager;

    @Inject
    StepListAdapter stepListAdapter;

    @Inject
    RecyclerView.LayoutManager stepListLayoutManager;

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

        ingredientsView.setAdapter(ingredientListAdapter);
        ingredientsView.setLayoutManager(ingredientListLayoutManager);

        stepsView.setAdapter(stepListAdapter);
        stepsView.setLayoutManager(stepListLayoutManager);

        return view;
    }

    public void setIngredientsCount(int count) {
        ingredientListAdapter.setItemCount(count);
    }

    public void setStepsCount(int count) {
        stepListAdapter.setItemCount(count);
    }
}
