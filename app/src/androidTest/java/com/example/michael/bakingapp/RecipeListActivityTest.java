package com.example.michael.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailActivity;
import com.example.michael.bakingapp.ui.RecipeList.RecipeListActivity;
import com.example.michael.bakingapp.utils.OkHttp3IdlingResource;
import com.example.michael.bakingapp.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
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
import static com.example.michael.bakingapp.utils.RecyclerViewMatcher.atPosition;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
    private OkHttp3IdlingResource idlingResource;

    @Rule
    public IntentsTestRule<RecipeListActivity> mActivityRule = new IntentsTestRule<>(RecipeListActivity.class);

    @Before
    public void registerIdlingResource() {
        BakingAppApplication application =
                (BakingAppApplication) InstrumentationRegistry.getTargetContext()
                        .getApplicationContext();

        idlingResource = OkHttp3IdlingResource.create("OkHttp", application.okHttpClient);

        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
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
}
