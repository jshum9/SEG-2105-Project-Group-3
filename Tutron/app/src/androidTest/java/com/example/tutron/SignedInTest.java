package com.example.tutron;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignedInTest {

    @Before
    public void launchActivity() {
        ActivityScenario.launch(SignedIn.class);
    }



    @Test
    public void test_isRoleTextDisplayed() {
        Espresso.onView(withId(R.id.role)).check(matches(withText("")));
    }
}
