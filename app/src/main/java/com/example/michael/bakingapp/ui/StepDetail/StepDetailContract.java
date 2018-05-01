package com.example.michael.bakingapp.ui.StepDetail;

import com.example.michael.bakingapp.data.schema.Step;

public interface StepDetailContract {
    interface View {
        public void setStep(Step step);
    }

    interface Presenter {
        void attach();

        void detach();
    }
}
