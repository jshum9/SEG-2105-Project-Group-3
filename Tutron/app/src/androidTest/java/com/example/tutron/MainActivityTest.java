package com.example.tutron;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> intentsTestRule =
            new ActivityScenarioRule<>(MainActivity.class);



    @Before
    public void setup(){
        Intents.init();
    }

    @After
    public void cleanup(){
        Intents.release();
    }

    @Test
    public void testRegisterBtnClick() {
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.registerBtn)).perform(click());

        intended(hasComponent(GetTutorOrStudent.class.getName()));
    }

    @Test
    public void testAdministratorSignIn(){
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.userEmail)).perform(typeText("Administrator"));
        onView(withId(R.id.userPassword)).perform(typeText("password"));
        onView(withId(R.id.loginBtn)).perform(click());

        intended(hasComponent(SignedIn.class.getName()));

    }

    @Test
    public void testActiveTutorSignIn(){
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.userEmail)).perform(typeText("testTutorEmail@gmail.com"));
        onView(withId(R.id.userPassword)).perform(typeText("password"));
        onView(withId(R.id.loginBtn)).perform(click());

        intended(hasComponent(SignedIn.class.getName()));
    }

    @Test
    public void testStudentSignIn(){
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.userEmail)).perform(typeText("Student@gmail.com"));
        onView(withId(R.id.userPassword)).perform(typeText("password"));
        onView(withId(R.id.loginBtn)).perform(click());

        intended(hasComponent(SignedIn.class.getName()));
    }


}

