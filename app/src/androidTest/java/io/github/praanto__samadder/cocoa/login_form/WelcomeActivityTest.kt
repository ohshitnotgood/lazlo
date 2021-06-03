package io.github.praanto__samadder.cocoa.login_form

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.praanto__samadder.cocoa.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class WelcomeActivityTest {

//    @get:Rule val activityScenarioRule = ActivityScenarioRule(WelcomeActivity::class.java)

    @Test
    fun isAllTextDisplayedCorrectly() {

        val activityScenarioRule = ActivityScenario.launch(WelcomeActivity::class.java)

        // Check if root layout is in view
        onView(withId(R.id.rl_welcome_activity)).check(matches(isDisplayed()))

        // Check correct welcome text
        onView(withId(R.id.tvWelcome)).check(matches(withText(R.string.tvWelcome)))

        // Check correct text inside continue button
        onView(withId(R.id.btnContinue)).check(matches(withText(R.string.btnContinue)))

    }

    @Test
    fun test_btnContinueLaunchesLoginForm() {

        val activityScenarioRule = ActivityScenario.launch(WelcomeActivity::class.java)

        // Click login button
        onView(withId(R.id.btnContinue)).perform(click())

        // Check if LoginForm is launched
        onView(withId(R.id.rl_activity_login_form)).check(matches(isDisplayed()))

    }
}