package com.example.android.bakingapplication;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.android.bakingapplication.view.activity.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
<<<<<<< HEAD
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {
	
	@Rule
	public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(
			MainActivity.class);
	
	
	@Test
	public void whenMainActivityLaunchesFirstCardDisplaysText() {
		onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.scrollToPosition(1))
		                                   .check(matches(hasDescendant(withText("Nutella Pie"))))
		                                   .check(matches(hasDescendant(withText("Steps: 6"))))
		                                   .check(matches(
				                                   hasDescendant(withText("Ingredients: 8"))))
		                                   .check(matches(hasDescendant(withText("Servings: 8"))));
	}
=======
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void whenMainActivityLaunchesTextViewsAreDisplayed() {
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withText("6 Steps")).check(matches(isDisplayed()));
        onView(withText("8 Ingredients")).check(matches(isDisplayed()));
        onView(withText("8 Servings")).check(matches(isDisplayed()));
    }

>>>>>>> 5c0ed547a85e7db9fe442a022a754c537632178b
}
