package com.example.android.bakingapplication;

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

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    /* REVIEW: TIGHTEN UP COMMENT--
    *  Had to cherry-pick these tests from different branch. :-(
    */

    /* REVIEW: QUESTION--
     * Do you even need this activity test rule?
     */

//    Isn't the activity test rule necessary for starting the activity that holds the views being tested?
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    /* REVIEW: FIX ME--
     *  With the changes I made, all four of these tests should all fail.
     *  Instead, all four are reporting false positives.
     *  What's the dealio?
     */

//
    @Test
    public void whenMainActivityLaunchesTextViewsAreDisplayed() {
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withText("6 Steps")).check(matches(isDisplayed()));
        onView(withText("8 Ingredients")).check(matches(isDisplayed()));
        onView(withText("8 Servings")).check(matches(isDisplayed()));
    }

}
