package com.example.michael.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailActivity;
import com.example.michael.bakingapp.ui.RecipeList.RecipeListActivity;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.michael.bakingapp.Utils.atPosition;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public IntentsTestRule<RecipeListActivity> mActivityRule = new IntentsTestRule<>(RecipeListActivity.class);

    @Before
    public void registerIdlingResource() {
        BakingAppApplication application =
                (BakingAppApplication) InstrumentationRegistry.getTargetContext()
                        .getApplicationContext();

        OkHttp3IdlingResource idlingResource = OkHttp3IdlingResource.create("OkHttp", application.okHttpClient);

        Espresso.registerIdlingResources(idlingResource);
    }

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void showRecipes() {
        onView(withId(R.id.recipes)).check(new RecyclerViewItemCountAssertion(4));
        onView(withId(R.id.recipes))
                .check(matches(atPosition(0, hasDescendant(withText("Nutella Pie")))));
    }

    @Test
    public void openRecipeDetail() {
        onView(withId(R.id.recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(hasComponent(RecipeDetailActivity.class.getName())));
    }

    // Source: https://stackoverflow.com/a/37339656/326574
    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }
}
