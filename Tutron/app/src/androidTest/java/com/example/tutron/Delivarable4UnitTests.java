package com.example.tutron;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class Delivarable4UnitTests {

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
    public void testStudentSignInAndEditProfile(){
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.userEmail)).perform(typeText("testStudentEmail@gmail.com"));
        onView(withId(R.id.userPassword)).perform(typeText("password"));
        onView(withId(R.id.loginBtn)).perform(click());

        intended(hasComponent(SignedIn.class.getName()));

        onView(withId(R.id.continueBtn)).perform(click());

        intended(hasComponent(StudentHomePage.class.getName()));

        onView(withId(R.id.editProfileBtn)).perform(click());

        intended(hasComponent((StudentProfile.class.getName())));
    }

    @Test
    public void studentAddTopic(){
        ActivityScenario.launch(StudentHomePage.class);

        onView(withId(R.id.addTopicBtn)).perform(click());

        intended(hasComponent(StudentTopicManagement.class.getName()));
    }

    @Test
    public void studentLogOff(){
        ActivityScenario.launch(StudentHomePage.class);

        onView(withId(R.id.logOffBtn)).perform(click());

        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void studentSearchTutor(){
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.userEmail)).perform(typeText("testStudentEmail@gmail.com"));
        onView(withId(R.id.userPassword)).perform(typeText("password"));
        onView(withId(R.id.loginBtn)).perform(click());

        intended(hasComponent(SignedIn.class.getName()));

        onView(withId(R.id.continueBtn)).perform(click());

        intended(hasComponent(StudentHomePage.class.getName()));

        onView(withId(R.id.addTopicBtn)).perform(click());

        intended(hasComponent(StudentTopicManagement.class.getName()));

        onView(withId(R.id.editTextTutorName)).perform(typeText("shawhin"));
        onView(withId(R.id.searchBtn)).perform(click());

        DataInteraction listItem = onData(anything())
                .inAdapterView(withId(R.id.tutorSearchResults))
                .atPosition(0); // Adjust the position based on your specific test case

        listItem.check(matches(isDisplayed()));

    }




}

