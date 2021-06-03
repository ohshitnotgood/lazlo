package io.github.praanto__samadder.cocoa.login_form

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.praanto__samadder.cocoa.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginFormTest {

    @get:Rule val activityScenarioRule = ActivityScenarioRule(LoginForm::class.java)

    @Test
    fun testIfActivityIsDisplayed() {
        onView(withId(R.id.rl_activity_login_form)).check(matches(isDisplayed()))
    }
}