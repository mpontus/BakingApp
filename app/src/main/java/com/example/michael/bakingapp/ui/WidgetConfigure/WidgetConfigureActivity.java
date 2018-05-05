package com.example.michael.bakingapp.ui.WidgetConfigure;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.ui.Widget.BakingAppWidget;
import com.example.michael.bakingapp.ui.Widget.WidgetUpdater;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * The configuration screen for the {@link BakingAppWidget BakingAppWidget} AppWidget.
 */
public class WidgetConfigureActivity extends DaggerAppCompatActivity
        implements WidgetConfigureContract.View {

    public static int getAppWidgetId(Activity activity) {
        // Find the widget id from the intent.
        Intent intent = activity.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            return extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        return AppWidgetManager.INVALID_APPWIDGET_ID;
    }

    private Recipe[] recipes;

    @Inject
    WidgetConfigureContract.Presenter presenter;

    @Inject
    WidgetUpdater widgetUpdater;

    @BindView(R.id.recipes)
    Spinner recipeView;

    @BindView(R.id.add_button)
    Button addButton;

    public WidgetConfigureActivity() {
        super();
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.baking_app_widget_configure);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.attach();
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.detach();
    }

    @Override
    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;

        ArrayList<String> recipeNames = new ArrayList<>();

        for (Recipe recipe : recipes) {
            recipeNames.add(recipe.getName());
        }

        recipeView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                recipeNames));
    }

    @Override
    public void finishSuccess(int widgetId) {
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @OnClick(R.id.add_button)
    public void onSubmit(View view) {
        if (recipes == null) {
            return;
        }

        int position = recipeView.getSelectedItemPosition();
        Recipe recipe = recipes[position];

        presenter.onSubmit(recipe);
    }
}

