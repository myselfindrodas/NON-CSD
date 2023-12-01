package com.grocery.sainik_grocery.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grocery.sainik_grocery.R


object BottomMenuHelper {

    fun showBadge(
        context: Context?,
        bottomNavigationView: BottomNavigationView,
        @IdRes itemId: Int,
        value: String?
    ) {
        removeBadge(bottomNavigationView, itemId)
        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        val badge: View = LayoutInflater.from(context)
            .inflate(R.layout.layout_cart_badge, bottomNavigationView, false)
        val text = badge.findViewById<TextView>(R.id.badge_text_view)
        text.text = value
        itemView.addView(badge)
    }

    fun removeBadge(bottomNavigationView: BottomNavigationView, @IdRes itemId: Int) {
        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        if (itemView.childCount == 3) {
            itemView.removeViewAt(2)
        }
    }
}