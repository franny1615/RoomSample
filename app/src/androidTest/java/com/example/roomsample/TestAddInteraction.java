package com.example.roomsample;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestAddInteraction {

    private String front;
    private String back;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void init() {
        // specify stuff you want to set up for test
        front = "this is front text";
        back = "this is the back text";
    }

    @Test
    public void interactWithAdd() {
        onView(withId(R.id.addButton)).perform(click());
        // check that we can put click item
        onView(withId(R.id.frontOfCard)).check(matches(isClickable()));
        onView(withId(R.id.backOfCard)).check(matches(isClickable()));
        // see if you can actually put stuff in
        onView(withId(R.id.frontOfCard)).perform(typeText(front),closeSoftKeyboard());
        onView(withId(R.id.backOfCard)).perform(typeText(back),closeSoftKeyboard());
        // check that we actually typed the stuff
        onView(withId(R.id.frontOfCard)).check(matches(withText(front)));
        onView(withId(R.id.backOfCard)).check(matches(withText(back)));
    }

}
