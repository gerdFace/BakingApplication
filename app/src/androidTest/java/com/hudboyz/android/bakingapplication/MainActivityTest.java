package com.hudboyz.android.bakingapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void whenMainActivityLaunchesTextViewsAreDisplayed() {
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withText("6 Steps")).check(matches(isDisplayed()));
        onView(withText("8 Ingredients")).check(matches(isDisplayed()));
        onView(withText("8 Servings")).check(matches(isDisplayed()));
    }

    /* REVIEW: QUESTION--
     * Why no test for multiple cards???
     *
     * Baggable offense! :-)
     */

}