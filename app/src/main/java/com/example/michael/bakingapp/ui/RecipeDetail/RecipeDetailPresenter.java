package com.example.michael.bakingapp.ui.RecipeDetail;

import com.example.michael.bakingapp.data.schema.Ingredient;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class RecipeDetailPresenter implements RecipeDetailContract.Presenter,
        RecipeDetailContract.IngredientItemPresenterFactory,
        RecipeDetailContract.StepItemPresenterFactory {

    private RecipeDetailContract.View view;
    private Single<Recipe> recipe;
    private CompositeDisposable compositeDisposable;
    private Provider<CompositeDisposable> compositeDisposableProvider;
    private RecipeDetailContract.SelectStepStrategy selectStepStrategy;

    @Inject
    public RecipeDetailPresenter(RecipeDetailContract.View view,
                                 Single<Recipe> recipe,
                                 CompositeDisposable compositeDisposable,
                                 Provider<CompositeDisposable> compositeDisposableProvider,
                                 RecipeDetailContract.SelectStepStrategy selectStepStrategy) {
        this.view = view;
        this.recipe = recipe;
        this.compositeDisposable = compositeDisposable;
        this.compositeDisposableProvider = compositeDisposableProvider;
        this.selectStepStrategy = selectStepStrategy;
    }

    @Override
    public void attach() {
        compositeDisposable.add(
                recipe.subscribe(recipe -> {
                    Ingredient[] ingredients = recipe.getIngredients();
                    Step[] steps = recipe.getSteps();

                    view.setIngredientCount(ingredients.length);
                    view.setStepCount(steps.length);
                })
        );
    }

    @Override
    public void detach() {
        compositeDisposable.dispose();
    }

    @Override
    public RecipeDetailContract.IngredientItemPresenter getIngredientItemPresenter(RecipeDetailContract.IngredientView view, int position) {
        return new IngredientPresenter(view,
                recipe.map(recipe -> recipe.getIngredients()[position]),
                compositeDisposableProvider.get());
    }

    @Override
    public RecipeDetailContract.StepItemPresenter getStepItemPresenter(RecipeDetailContract.StepView view, int position) {
        return new StepPresenter(view,
                recipe.map(recipe -> recipe.getSteps()[position]),
                compositeDisposableProvider.get(),
                step -> {
                    selectStepStrategy.onSelectStep(step);
                });
    }

    static class IngredientPresenter implements RecipeDetailContract.IngredientItemPresenter {
        private RecipeDetailContract.IngredientView view;
        private Single<Ingredient> ingredient;
        private CompositeDisposable compositeDisposable;

        IngredientPresenter(RecipeDetailContract.IngredientView view, Single<Ingredient> ingredient, CompositeDisposable compositeDisposable) {
            this.view = view;
            this.ingredient = ingredient;
            this.compositeDisposable = compositeDisposable;
        }

        @Override
        public void attach() {
            compositeDisposable.add(
                    ingredient.subscribe(ingredient -> {
                        view.setIngredient(ingredient.getIngredient());
                        view.setQuantity(ingredient.getQuantity());
                        view.setMeasure(getViewMeasurement(ingredient.getMeasure()));
                    })
            );
        }

        @Override
        public void detach() {
            compositeDisposable.dispose();
        }

        private RecipeDetailContract.Measure getViewMeasurement(Ingredient.Measure measure) {
            switch (measure) {
                case CUP:
                    return RecipeDetailContract.Measure.CUP;
                case TBLSP:
                    return RecipeDetailContract.Measure.TBLSP;
                case TSP:
                    return RecipeDetailContract.Measure.TSP;
                case K:
                    return RecipeDetailContract.Measure.K;
                case G:
                    return RecipeDetailContract.Measure.G;
                case OZ:
                    return RecipeDetailContract.Measure.OZ;
                case UNIT:
                    return RecipeDetailContract.Measure.UNIT;
                default:
                    throw new RuntimeException("Unknown measurement");
            }
        }
    }

    static class StepPresenter implements RecipeDetailContract.StepItemPresenter {
        private RecipeDetailContract.StepView view;
        private Single<Step> step;
        private CompositeDisposable compositeDisposable;
        private OnClickListener onClickListener;

        StepPresenter(RecipeDetailContract.StepView view, Single<Step> step, CompositeDisposable compositeDisposable, OnClickListener onClickListener) {
            this.view = view;
            this.step = step;
            this.compositeDisposable = compositeDisposable;
            this.onClickListener = onClickListener;
        }


        @Override
        public void attach() {
            compositeDisposable.add(
                    step.subscribe(step -> {
                        view.setTitle(step.getShortDescription());
                    })
            );
        }

        @Override
        public void detach() {
            compositeDisposable.dispose();
        }

        @Override
        public void onClick() {
            compositeDisposable.add(
                    step.subscribe(step -> {
                        if (onClickListener != null) {
                            onClickListener.onClick(step);
                        }
                    })
            );
        }

        interface OnClickListener {
            void onClick(Step step);
        }
    }
}
