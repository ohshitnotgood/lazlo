package io.github.praanto__samadder.cocoa.boo_hoo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.boilerplate.login_forms.SceneOneActivity
import io.github.praanto__samadder.cocoa.experimental.Experimental
import kotlinx.android.synthetic.main.activity_boo_hoo_whore.*

class BooHooWhore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boo_hoo_whore)

        nayeem_er_putki_chudi.setOnClickListener{
            val intent = Intent(this, Experimental::class.java)
            startActivity(intent)
        }
    }
}