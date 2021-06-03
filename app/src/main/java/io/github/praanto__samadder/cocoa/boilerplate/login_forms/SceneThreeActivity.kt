package io.github.praanto__samadder.cocoa.boilerplate.login_forms

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import io.github.praanto__samadder.cocoa.R
import kotlinx.android.synthetic.main.activity_scene_three.*

/**Animates password field into view.
 *
 * Starts [SceneFourActivity] after animation finishes
 *
 * Multiple activities used for the animation.
 * + MotionLayout too complicated to edit using XML.
 * + Motion Editor too buggy to work with.
 *
 * @author Praanto Samadder
 */

class SceneThreeActivity : AppCompatActivity() {

    private lateinit var e: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_three)


        passwordAnimation.apply {
            // Get email from previous activity and set as email field text
            e = intent.getStringExtra("email")!!
            uni_edt_email.setText(e)

            post { transitionToEnd() }

            // Start SceneFourActivity when animation ends
            setTransitionListener(OnTransitionEnd())
        }
    }

    private fun startSceneFour () {
        val password = uni_edt_pass.text.toString()

        overridePendingTransition(0, 0)
        val intent = Intent(this, SceneFourActivity::class.java)

        intent.putExtra("email", e)
        intent.putExtra("password", password)

        startActivity(intent)
        finish()
    }

    override fun finish() {
        overridePendingTransition(0, 0)
        super.finish()
    }

    inner class OnTransitionEnd : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
        }

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            startSceneFour()
        }

        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
        }

    }
}