package com.example.android.bakingapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapplication.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UIDisplaysCorrectRecipeDetailsTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkDessertNameFirstRecipe() {
        onView(withId(R.id.dessert_name)).check(matches(withText("Nutella Pie")));
    }

    @Test
    public void checkNumberOfStepsFirstRecipe() {
        onView(withId(R.id.number_of_steps)).check(matches(withText("6 Steps")));
    }

    @Test
    public void checkNumberOfIngredientsFirstRecipe() {
        onView(withId(R.id.number_of_ingredients)).check(matches(withText("8 Ingredients")));
    }

    @Test
    public void checkNumberOfServingsFirstRecipe() {
        onView(withId(R.id.number_of_servings)).check(matches(withText("8 Servings")));
    }
}
