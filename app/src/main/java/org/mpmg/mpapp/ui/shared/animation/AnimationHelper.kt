package org.mpmg.mpapp.ui.shared.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View


object AnimationHelper {

    fun rotate(v: View, angle: Float) {
        v.animate().setDuration(200).rotation(angle)
    }

    fun showView(views: List<View>) {
        views.forEach { v ->
            v.visibility = View.VISIBLE
            v.alpha = 0f
            v.translationX = v.width.toFloat()
            v.animate()
                .setDuration(200)
                .translationX(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(1f)
                .start()
        }
    }

    fun hideView(views: List<View>) {
        views.forEach { v ->
            v.visibility = View.VISIBLE
            v.alpha = 1f
            v.translationX = 0f
            v.animate()
                .setDuration(200)
                .translationX(v.width.toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        v.visibility = View.GONE
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(0f)
                .start()
        }
    }

}