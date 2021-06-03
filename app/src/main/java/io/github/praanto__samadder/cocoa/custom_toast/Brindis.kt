package io.github.praanto__samadder.cocoa.custom_toast

import android.content.Context
import android.view.View
import androidx.cardview.widget.CardView
import io.github.praanto__samadder.cocoa.R

/**
 * Brindis stands for Toast in Spanish, at least according to Google Translate it does.
 * As that translation probably suggests, this class creates a custom Toast message.
 *
 * @author Praanto Samadder
 */
class Brindis {

    fun makeToast(rootLayout: View) {

        val context = rootLayout.context

        val backgroundCardView = CardView(context)
        backgroundCardView.radius = 20F
        backgroundCardView.setCardBackgroundColor(context.getColor(R.color.UIKitBlue))

        backgroundCardView.addView(rootLayout)

    }
}