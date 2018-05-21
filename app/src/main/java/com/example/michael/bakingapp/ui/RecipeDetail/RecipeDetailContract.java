package com.example.michael.bakingapp.ui.RecipeDetail;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;

public interface RecipeDetailContract {

    interface Presenter {
        void attach();

        void detach();

        void onStepClick(Step step);
    }

    interface View {
        void setRecipe(Recipe recipe);

        void setDefaultStep(Step step);

        void setStep(Step step);

        boolean isMasterDetailLayout();
    }
}
