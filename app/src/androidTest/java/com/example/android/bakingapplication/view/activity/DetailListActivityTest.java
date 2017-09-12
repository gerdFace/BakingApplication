package com.example.android.bakingapplication.view.activity;


import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.android.bakingapplication.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailListActivityTest {

    @Rule
    public ActivityTestRule<DetailListActivity> detailListActivityTestRule = new ActivityTestRule<>(DetailListActivity.class, true, false);

    @Before
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("RECIPE_ID", 0);
        intent.putExtra("NAME_OF_FOOD", "Nutella Pie");
        detailListActivityTestRule.launchActivity(intent);
    }
    @Test
    public void detailListActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_detail_list), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));


        ViewInteraction textView2 = onView(
                allOf(withId(R.id.ingredient_dropbar_text), withText("Ingredients"),
                        childAtPosition(
                                allOf(withId(R.id.ingredient_card_container),
                                        childAtPosition(
                                                withId(R.id.fragment_detail_list_constraint_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.ingredient_card_container),
                                childAtPosition(
                                        withId(R.id.fragment_detail_list_constraint_container),
                                        0)),
                        1),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
