package com.grocery.sainik_grocery.utils

import androidx.appcompat.app.AppCompatActivity
import com.grocery.sainik_grocery.R

object AnimUtils {

    fun LeftToRightAnim(activity: AppCompatActivity) {
        activity.overridePendingTransition(
            R.anim.animation_leave,
            R.anim.animation_enter
        )
    }

    fun RightToLeftAnim(activity: AppCompatActivity) {
        activity.overridePendingTransition(
            R.anim.animation_leave,
            R.anim.animation_enter
        )
    }

    fun FadeOutAnim(activity: AppCompatActivity) {
        activity.overridePendingTransition(
            R.anim.fade_in, R.anim.fade_out
        )
    }

    fun FadeInAnim(activity: AppCompatActivity) {
        activity.overridePendingTransition(
            R.anim.fade_out, R.anim.fade_in
        )
    }
}