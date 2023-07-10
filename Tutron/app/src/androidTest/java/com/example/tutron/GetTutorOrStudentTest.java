package com.example.tutron;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GetTutorOrStudentTest {

    @Rule
    public IntentsTestRule<GetTutorOrStudent> intentsTestRule =
            new IntentsTestRule<>(GetTutorOrStudent.class);

    @Test
    public void testNavigateToTutor() {
        // Perform a click on the tutor button
        onView(withId(R.id.tutorSelectBtn)).perform(click());

        // Check if the intent to start the Tutor activity was sent
        intended(hasComponent(Tutor.class.getName()));
    }

    @Test
    public void testNavigateToStudent() {
        // Perform a click on the student button
        onView(withId(R.id.studentSelectBtn)).perform(click());

        // Check if the intent to start the Student activity was sent
        intended(hasComponent(Student.class.getName()));
    }

    @Test
    public void testNavigateBack() {
        // Perform a click on the back button
        onView(withId(R.id.backButton)).perform(click());

        // Check if the intent to start the MainActivity was sent
        intended(hasComponent(MainActivity.class.getName()));
    }
}
