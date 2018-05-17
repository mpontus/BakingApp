package com.example.michael.bakingapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.ui.StepDetail.StepDetailActivity;
import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.io.InputStreamReader;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {
    @Rule
    public IntentsTestRule<StepDetailActivity> mActivityRule =
            new IntentsTestRule<StepDetailActivity>(StepDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    BakingAppApplication application =
                            (BakingAppApplication) InstrumentationRegistry.getTargetContext()
                                    .getApplicationContext();
                    Gson gson = application.gson;

                    // Source: https://stackoverflow.com/a/35071636/326574
                    Resources resources = InstrumentationRegistry.getContext().getResources();
                    InputStream inputStream = resources.openRawResource(com.example.michael.bakingapp.test.R.raw.sample_recipe);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    Recipe recipe = gson.fromJson(inputStreamReader, Recipe.class);

                    Intent intent = new Intent();
                    intent.putExtra(StepDetailActivity.EXTRA_RECIPE, gson.toJson(recipe));
                    intent.putExtra(StepDetailActivity.EXTRA_STEP, gson.toJson(recipe.getSteps()[0]));

                    return intent;
                }
            };

    @Test
    public void showDescription() {
        onView(withId(R.id.description)).check(matches(withText("Recipe Introduction")));
    }
}
