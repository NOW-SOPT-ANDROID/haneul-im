package com.sopt.now

import androidx.annotation.DrawableRes

data class Profile(
    @DrawableRes val profileImage: Int,
    val name: String,
    var selfDescription: String  // 변경 가능하도록 var로 변경
) {
    fun updateDescription(newDescription: String) {
        selfDescription = newDescription
    }
}