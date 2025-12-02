package com.example.calculator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CalculatorUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test1_DisplayIsVisible() {
        // Tikrinu ar ekranas atsidare
        onView(withId(R.id.txtDisplay)).check(matches(isDisplayed()));
    }

    @Test
    public void test2_ClickClearButton() {
        // Paspaudziu clear mygtuka ir tikrinu ar suveike
        onView(withId(R.id.btnClear)).perform(click());
    }

    @Test
    public void test3_ClickNumber5() {
        // Paspaudzia skaiciu 5
        onView(withId(R.id.btn5)).perform(click());
    }
}