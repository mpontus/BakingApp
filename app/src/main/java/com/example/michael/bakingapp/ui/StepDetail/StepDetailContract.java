package com.example.michael.bakingapp.ui.StepDetail;

public interface StepDetailContract {
    interface View {
        void setVideoUrl(String videoUrl);

        void setDescription(String description);
    }

    interface Presenter {
        void attach();

        void detach();
    }
}
