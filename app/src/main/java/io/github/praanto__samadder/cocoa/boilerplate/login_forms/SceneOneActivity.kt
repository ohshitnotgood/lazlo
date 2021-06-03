package io.github.praanto__samadder.cocoa.boilerplate.login_forms

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import io.github.praanto__samadder.cocoa.R
import kotlinx.android.synthetic.main.activity_scene_one.*

/**
 * User presses Get Started button.
 *
 * Button animates up and converts into an EditText
 */
class SceneOneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_one)

        uni_btn_get_started.setOnClickListener {

            val nextActivityIntent = Intent(this, SceneTwoActivity::class.java)

            ml_root_layout.apply {
                post { transitionToEnd() }
                /* After MotionLayout transition has been completed, the app moves to the next
                activity without any animation and removes previous activity from back-stack. */
                setTransitionListener(OnTransitionHasFinished(nextActivityIntent))
            }
        }
    }

    override fun finish() {
        overridePendingTransition(0, 0)
        super.finish()
    }

    inner class OnTransitionHasFinished (private val intent: Intent) : MotionLayout.TransitionListener {

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            startSceneTwo()
        }

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

        private fun startSceneTwo() {
            overridePendingTransition(0, 0)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION

            startActivity(intent)
            finish()
        }
    }
}