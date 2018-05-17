package com.example.michael.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailActivity;
import com.example.michael.bakingapp.ui.StepDetail.StepDetailActivity;
import com.example.michael.bakingapp.utils.RecyclerViewItemCountAssertion;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.io.InputStreamReader;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.michael.bakingapp.utils.RecyclerViewMatcher.atPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {
    @Rule
    public IntentsTestRule<RecipeDetailActivity> mActivityRule =
            new IntentsTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
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
                    intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, gson.toJson(recipe));

                    return intent;
                }
            };

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }


    @Test
    public void showIngredirents() {
        onView(withId(R.id.ingredients)).check(new RecyclerViewItemCountAssertion(9));
        onView(withId(R.id.ingredients))
                .check(matches(atPosition(0, hasDescendant(withText("Graham Cracker crumbs")))));
    }

    @Test
    public void showSteps() {
        onView(withId(R.id.steps)).check(new RecyclerViewItemCountAssertion(7));
        onView(withId(R.id.steps))
                .check(matches(atPosition(0, hasDescendant(withText("Recipe Introduction")))));
    }

    @Test
    public void opensStepDetail() {
        onView(withId(R.id.steps))
                .perform(scrollTo(), RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(hasComponent(StepDetailActivity.class.getName())));
    }
}
