package com.sopt.now.compose

import androidx.annotation.DrawableRes

data class Profile(
    @DrawableRes val profileImage: Int,
    val name: String,
    var selfDescription: String
)