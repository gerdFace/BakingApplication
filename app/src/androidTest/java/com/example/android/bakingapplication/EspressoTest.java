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
}
