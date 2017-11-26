package com.lavekush.telstra;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.lavekush.telstra.activity.HomeActivity;
import com.lavekush.telstra.activity.SplashScreenMainActivity;
import com.lavekush.telstra.util.Util;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule
            = new ActivityTestRule<>(
            HomeActivity.class,
            true,     // initialTouchMode
            true);   // launchActivity. False to customize the intent

    @Test
    public void checkInternetConnectivityMethod() {
        assertTrue(Util.isConnectedWithInternet(mActivityRule.getActivity()));
    }


    @Test
    public void checkOfflineErrorMessage() {
        // Please turn off network connectivity for this test case
        if (!Util.isConnectedWithInternet(mActivityRule.getActivity())) {
            Espresso.onView(withId(R.id.text_error_message)).check(matches(withText(R.string.error_offline_text)));
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void checkApiSuccess() {

        //Taking delay to ensure api data has arrived
        SystemClock.sleep(2000);

        if (Util.isConnectedWithInternet(mActivityRule.getActivity())) {

            //Checking is list displayed or not
            Espresso.onView(withId(R.id.list)).check(matches(ViewMatchers.isDisplayed()));
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void checkApiSwipeToRefresh() {

        //Taking delay to ensure api data has arrived
        SystemClock.sleep(2000);

        Espresso.onView(withId(R.id.swipe_to_list))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));
    }


    /**
     * Custom validator method for swipe-to-refresh features
     * @param action
     * @param constraints
     * @return
     */
    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }
}