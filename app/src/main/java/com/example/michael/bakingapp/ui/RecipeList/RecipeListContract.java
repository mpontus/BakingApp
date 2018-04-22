package com.example.michael.bakingapp.ui.RecipeList;

public interface RecipeListContract {

    interface View {
        void setRecipeCount(int count);
    }

    interface Presenter {
        void attach();

        void detach();
    }

    interface ItemPresenterFactory {
        ItemPresenter getItemPresenter(ItemView view, int position);
    }

    interface ItemView {
        void setTitle(String title);
    }

    interface ItemPresenter {
        void attach();

        void detach();
    }
}
