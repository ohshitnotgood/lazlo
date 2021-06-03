package io.github.praanto__samadder.cocoa.dashboardActivity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.databinding.ActivityDashboardBinding
import io.github.praanto__samadder.cocoa.fire.Helpers.vibrate
import io.github.praanto__samadder.cocoa.fire.Timestamps
import io.github.praanto__samadder.cocoa.fire.UserID.fetchLastUserTab
import io.github.praanto__samadder.cocoa.fire.UserID.saveLastUsedTab
import io.github.praanto__samadder.cocoa.fire.UserID.saveUserID
import io.github.praanto__samadder.cocoa.fragments.chemistry.ChemistryFragment
import io.github.praanto__samadder.cocoa.fragments.physics.PhysicsFragment
import io.github.praanto__samadder.cocoa.fragments.pure.PureFragment
import io.github.praanto__samadder.cocoa.fragments.statistics.StatisticsFragment
import io.github.praanto__samadder.cocoa.login_form.LoginForm
import io.github.praanto__samadder.cocoa.model.ChannelID
import io.github.praanto__samadder.cocoa.model.ChannelName
import io.github.praanto__samadder.cocoa.model.NotificationId
import io.github.praanto__samadder.cocoa.model.NotificationUtils
import io.github.praanto__samadder.cocoa.receivers.DailyNotificationBroadcastReceiver
import java.util.*

/**
 * DashboardActivity class holds the fragment container and the bottom navigation bar.
 * @author Praanto Samadder
 */

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding
    private lateinit var lastTab : String

    /* Counts the number of times the Physics tab is clicked. */
    private var physicsClickCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        initializeNotificationChannels()
        initializeAppEntryMusic()
        initDailyClassReminder()

        /* Change height of fl_wrapper to fit above Bottom Nav Bar */
        val heightBottomNav: Int = binding.bottomNav.height
        val parentHeight = binding.flWrapper.height
        binding.flWrapper.layoutParams.height = parentHeight - heightBottomNav

        lastTab = fetchLastUserTab(application)

        resumeTab(lastTab)

        /* Add select listener on the bottom navigation bar. Changes fragment contained in fl_wrapper. */
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btPhy -> {
                    lastTab = "physics"
                    vibrate(application)
                    switchWrapperFragment(PhysicsFragment())
                }
                R.id.btChem -> {
                    lastTab = "chemistry"
                    vibrate(application)
                    switchWrapperFragment(ChemistryFragment())
                }
                R.id.btP5 -> {
                    lastTab = "p3"
                    vibrate(application)
                    switchWrapperFragment(PureFragment())
                }
                R.id.btStats -> {
                    lastTab = "statistics"
                    vibrate(application)
                    switchWrapperFragment(StatisticsFragment())
                }
            }
            true
        }

        binding.bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.btPhy -> logOut()
                else -> physicsClickCounter = 0
            }
        }
    }

    /**
     * Checks for logout requests and initiates if met.
     */
    private fun logOut() {
        vibrate(application)
        physicsClickCounter++
        if (physicsClickCounter >= 5) {
            val intent = Intent(this, LoginForm::class.java)
            startActivity(intent)
            saveUserID("", application)
            finish()
        }
    }

    private fun resumeTab(lastTab: String) {
        when (lastTab) {
            "physics" -> {
                binding.bottomNav.selectedItemId = R.id.btPhy
                switchWrapperFragment(PhysicsFragment())
            }
            "statistics" -> {
                binding.bottomNav.selectedItemId = R.id.btStats
                switchWrapperFragment(StatisticsFragment())
            }
            "p3" -> {
                binding.bottomNav.selectedItemId = R.id.btP5
                switchWrapperFragment(PureFragment())
            }
            "chemistry" -> {
                binding.bottomNav.selectedItemId = R.id.btChem
                switchWrapperFragment(ChemistryFragment())
            }
            "" -> {
                binding.bottomNav.selectedItemId = R.id.btPhy
                switchWrapperFragment(PhysicsFragment())
            }
        }
    }

    /**
     * Changes the current fragment container inside fragment container.
     */
    private fun switchWrapperFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    override fun onPause() {
        super.onPause()
        physicsClickCounter = 0
        saveLastUsedTab(lastTab, application)
    }

    private fun initializeAppEntryMusic() {
        val fl: Boolean = intent.getBooleanExtra("fl", false)
        if (fl) {
            val mediaPlayer = MediaPlayer.create(this, R.raw.intro_1)
            mediaPlayer.start()
        }
    }

    private fun initializeNotificationChannels() {
        NotificationUtils.createNotificationChannel(context = this, channelID = ChannelID.HOMEWORK_REMINDER,
            channelName = ChannelName.HOMEWORK_REMINDER, channelDescription = "Notifies you of a upcoming homework deadline."
        )

        NotificationUtils.createNotificationChannel(context = this, channelID = ChannelID.DAILY_CLASS_REMINDER,
            channelName = ChannelName.DAILY_CLASS_REMINDER, channelDescription = "Notifies you of a class 10 minutes before it begins."
        )
    }

    private fun initDailyClassReminder() {
        val hasInitialised = getSharedPreferences(ChannelID.DAILY_CLASS_REMINDER, Context.MODE_PRIVATE)
            .getBoolean(ChannelID.DAILY_CLASS_REMINDER, false)

        if (!hasInitialised) {
            // Sunday
            createNotification("Your Sunday Pure Mathematics class begins in 10 minutes",
                "Pure Mathematics Class Reminder", "22 November 2020", 11, 30
            )

            createNotification("Your Sunday Physics class begins in 10 minutes", "Physics Class Reminder",
                "22 November 2020", 12, 50
            )

            // Monday
            createNotification("Your Monday Pure Mathematics class begins in 10 minutes",
                "Pure Mathematics Class Reminder", "23 November 2020", 9, 40
            )

            createNotification("Your Monday Physics class begins in 10 minutes", "Physics Class Reminder",
                "23 November 2020", 12, 50
            )

            // Tuesday
            createNotification("Your Tuesday Statistics class begins in 10 minutes",
                "Statistics Class Reminder", "24 November 2020", 9, 40
            )

            createNotification("Your Monday Pure Mathematics class begins in 10 minutes",
                "Pure Mathematics Class Reminder", "24 November 2020", 11, 30
            )

            // Wednesday
            createNotification("Your Wednesday Chemistry class begins in 10 minutes", "Chemistry Class Reminder",
                "25 November 2020", 8, 20
            )

            createNotification("Your Tuesday Statistics class begins in 10 minutes", "Statistics Class Reminder",
                "25 November 2020", 9, 40
            )

            // Thursday
            createNotification("Your Thursday Chemistry class begins in 10 minutes", "Chemistry Class Reminder",
                "26 November 2020", 8, 20
            )

            createNotification("Your Thursday Statistics class begins in 10 minutes", "Statistics Class Reminder",
                "26 November 2020", 11, 30
            )

            getSharedPreferences(ChannelID.DAILY_CLASS_REMINDER, Context.MODE_PRIVATE).edit()
                .putBoolean(ChannelID.DAILY_CLASS_REMINDER, true).apply()
        }
    }

    private fun createNotification(notificationDesc: String, notificationTitle: String, date: String, hour: Int, minute: Int) {

        val calendar = Calendar.getInstance().apply {
            timeInMillis = Timestamps.convertToUnixTime(date)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val intent = Intent(this, DailyNotificationBroadcastReceiver::class.java)
        intent.apply {
            putExtra("notificationDesc", notificationDesc)
            putExtra("notificationId", NotificationId.DAILY_HOMEWORK_REMINDER)
            putExtra("notificationTitle", notificationTitle)
            putExtra("channelId", ChannelID.DAILY_CLASS_REMINDER)
        }

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, calendar.timeInMillis,
            1000 * 7 * 24 * 60 * 60, pendingIntent)
    }
}