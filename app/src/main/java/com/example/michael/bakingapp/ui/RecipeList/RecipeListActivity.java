package com.example.michael.bakingapp.ui.RecipeList;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.michael.bakingapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class RecipeListActivity extends DaggerAppCompatActivity implements RecipeListContract.View {

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

        this.presenter.attach();
    }

    @Override
    public void setRecipeCount(int count) {
        this.movieListAdapter.setItemCount(count);
    }
}
