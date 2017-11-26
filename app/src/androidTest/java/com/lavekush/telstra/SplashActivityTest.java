package com.lavekush.telstra;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.lavekush.telstra.activity.SplashScreenMainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashScreenMainActivity> activityRule
            = new ActivityTestRule<>(
            SplashScreenMainActivity.class,
            true,     // initialTouchMode
            true);   // launchActivity. False to customize the intent

    @Test
    public void launchSplash() {
        Espresso.onView(withId(R.id.text_company_web_addr)).check(matches(withText(R.string.url_company)));
    }
}