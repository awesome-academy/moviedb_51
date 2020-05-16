package com.sunasterisk.moviedb_51.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.sunasterisk.moviedb_51.R

fun FragmentActivity.addFragment(
    id: Int,
    fragment: Fragment,
    animationTypes: AnimationTypes = AnimationTypes.NONE,
    addToBackStack: Boolean = false,
    tag: String? = null
) = supportFragmentManager.beginTransaction().apply {
    when (animationTypes) {
        AnimationTypes.RIGHT_TO_LEFT -> setCustomAnimations(
            R.anim.right_to_left,
            R.anim.exit_right_to_left,
            R.anim.left_to_right,
            R.anim.exit_left_to_right
        )
        AnimationTypes.LEFT_TO_RIGHT -> setCustomAnimations(
            R.anim.left_to_right,
            R.anim.exit_left_to_right,
            R.anim.right_to_left,
            R.anim.exit_right_to_left
        )
        AnimationTypes.OPEN -> {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        AnimationTypes.NONE -> {
        }
    }
}.add(id, fragment).apply {
    if (addToBackStack) addToBackStack(tag)
}.commit()
