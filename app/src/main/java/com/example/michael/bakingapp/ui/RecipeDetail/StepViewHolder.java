package com.example.michael.bakingapp.ui.RecipeDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.michael.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepViewHolder extends RecyclerView.ViewHolder
        implements RecipeDetailContract.StepView {

    private RecipeDetailContract.StepItemPresenter presenter;

    @BindView(R.id.title)
    TextView titleView;

    public StepViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void attachPresenter(RecipeDetailContract.StepItemPresenter presenter) {
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
