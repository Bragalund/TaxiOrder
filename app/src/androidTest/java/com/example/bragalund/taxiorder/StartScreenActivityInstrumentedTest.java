package com.example.bragalund.taxiorder;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bragalund.taxiorder.Activities.StartScreenActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class StartScreenActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<StartScreenActivity> mActivityRule = new ActivityTestRule<>(
            StartScreenActivity.class);



    // Test wont work because of animation in StartScreenActivity, it makes espresso:
    // java.lang.RuntimeException: Could not launch intent Intent within 45 seconds.
    // There could be an animation or something constantly repainting the screen.

    // Solution:
    // Disable doAnimation()-method in onCreate in StartScreenActivity.class to get this test to work.
    @Test
    public void clickStartScreen_LaunchMapActivity_Test(){
        onView(withId(R.id.next_intent_button)).perform(click());
        onView(withId(R.id.google_map_fragment)).check(matches(allOf(isDescendantOfA(withId(R.id.bottom_fragment_container)), isDisplayed())));
    }


}
