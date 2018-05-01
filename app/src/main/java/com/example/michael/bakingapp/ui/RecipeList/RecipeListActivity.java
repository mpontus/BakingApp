package com.example.michael.bakingapp.ui.RecipeList;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class RecipeListActivity extends DaggerAppCompatActivity
        implements RecipeListContract.View, RecipeListAdapter.ViewHolder.OnClickListener {

    @Inject
    Gson gson;

    @Inject
    RecipeListContract.Presenter presenter;

    @Inject
    RecipeListAdapter movieListAdapter;

    @Inject
    RecyclerView.LayoutManager movieListLayoutManager;

    @BindView(R.id.recipes)
    RecyclerView recipesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        ButterKnife.bind(this);

        recipesView.setAdapter(movieListAdapter);
        recipesView.setLayoutManager(movieListLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.presenter.attach();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.presenter.detach();
    }

    public void setRecipes(Recipe[] recipes) {
        movieListAdapter.setRecipes(recipes);
    }

    @Override
    public void onClick(Recipe recipe) {
        presenter.onRecipeClick(recipe);
    }
}
