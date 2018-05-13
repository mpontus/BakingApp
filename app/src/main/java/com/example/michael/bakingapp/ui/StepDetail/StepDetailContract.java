package com.example.michael.bakingapp.ui.StepDetail;

import com.example.michael.bakingapp.data.schema.Step;

public interface StepDetailContract {
    interface View {
        void setStep(Step step);

        void setNextStepHidden(boolean isHidden);

        void setPrevStepHidden(boolean isHidden);
    }

    interface Presenter {
        void attach();

        void detach();

        void onNextClick();

        void onPrevClick();
    }
}
