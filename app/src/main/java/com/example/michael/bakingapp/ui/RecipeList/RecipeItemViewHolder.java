package com.example.michael.bakingapp.ui.RecipeList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.michael.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeItemViewHolder extends RecyclerView.ViewHolder implements RecipeListContract.ItemView {
    private RecipeListContract.ItemPresenter presenter;

    @BindView(R.id.title)
    TextView titleView;

    RecipeItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void attachPresenter(RecipeListContract.ItemPresenter presenter) {
        if (this.presenter != null) {
            this.presenter.detach();
        }

        this.presenter = presenter;

        presenter.attach();
    }

    @Override
    public void setTitle(String title) {
        titleView.setText(title);
    }
}
