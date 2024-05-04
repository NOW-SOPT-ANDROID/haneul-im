package com.sopt.now

import androidx.annotation.DrawableRes

data class Profile(
    @DrawableRes val profileImage: Int,
    val name: String,
    var selfDescription: String
) {
    fun updateDescription(newDescription: String) {
        selfDescription = newDescription
    }
}