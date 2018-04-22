package com.example.michael.bakingapp.ui.RecipeList;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.Repository;
import com.example.michael.bakingapp.di.ActivityContext;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;

@Module
public abstract class RecipeListModule {
    @Binds
    @ActivityContext
    abstract Context provideActivityContext(RecipeListActivity activity);

    @Binds
    abstract RecipeListContract.View provideView(RecipeListActivity activity);

    @Binds
    abstract RecipeListContract.Presenter providePresenter(RecipeListPresenter presenter);

    @Binds
    abstract RecipeListContract.ItemPresenterFactory provideItemPresenterFactory(
            RecipeListPresenter presenter
    );

    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(@ActivityContext Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    static Single<Recipe[]> provideRecipes(Repository repository) {
        return repository.getRecipes();
    }
}
