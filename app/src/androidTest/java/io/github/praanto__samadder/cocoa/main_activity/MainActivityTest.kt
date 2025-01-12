package io.github.praanto__samadder.cocoa.main_activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.praanto__samadder.cocoa.R
import kotlinx.android.synthetic.main.activity_welcome.view.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityLaunch_Test() {
        // Check if Welcome Activity is launched
        onView(withId(R.id.rl_welcome_activity)).check(matches(isDisplayed()))
    }
}