package com.example.michael.bakingapp.ui.RecipeDetail;

import com.example.michael.bakingapp.data.schema.Step;

public interface RecipeDetailContract {

    interface Presenter {
        void attach();

        void detach();
    }

    interface View {
        void setIngredientCount(int count);

        void setStepCount(int count);

        void setStepVideoUrl(String videoUrl);

        void setStepDescription(String description);

        void launchStepActivity(Step step);
    }

    interface IngredientView {
        void setIngredient(String ingredient);

        void setQuantity(float quantity);

        void setMeasure(Measure measure);
    }

    interface IngredientItemPresenter {
        void attach();

        void detach();
    }

    interface IngredientItemPresenterFactory {
        IngredientItemPresenter getIngredientItemPresenter(IngredientView view, int position);
    }

    interface StepView {
        void setTitle(String title);
    }

    interface StepItemPresenter {
        void attach();

        void detach();

        void onClick();
    }

    interface StepItemPresenterFactory {
        StepItemPresenter getStepItemPresenter(StepView view, int position);
    }

    interface SelectStepStrategy {
        void onSelectStep(Step step);
    }

    enum Measure {
        CUP,
        TBLSP,
        TSP,
        K,
        G,
        OZ,
        UNIT
    }
}
