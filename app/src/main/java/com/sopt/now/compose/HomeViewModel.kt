package com.sopt.now.compose

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val profile = Profile(
        profileImage = R.drawable.dog1,
        name="임하늘",
        selfDescription = "시험 시러")

    val mockFriendList = listOf<Friend>(
        Friend(
            profileImage = R.drawable.dog2,
            name = "이의경",
            selfDescription = "안드로이드 화이팅",
        ),
        Friend(
            profileImage = R.drawable.dog3,
            name = "공세영",
            selfDescription = "이 강아지들은 제가",
        ),
        Friend(
            profileImage = R.drawable.dog4,
            name = "신민석",
            selfDescription = "키우는 강아지들로",
        ),Friend(
            profileImage = R.drawable.dog10,
            name = "우상욱",
            selfDescription = "얘는 별이",
        ),
        Friend(
            profileImage = R.drawable.dog5,
            name = "공세영",
            selfDescription = "얘는 달이입니다",
        ),
        Friend(
            profileImage = R.drawable.dog6,
            name = "신민석",
            selfDescription = "ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ",
        ),Friend(
            profileImage = R.drawable.dog7,
            name = "이의경",
            selfDescription = "다들 빨리 끝내고 뒤풀이 가고 싶지? ㅎㅎ 아직 반도 안왔어 ^&^",
        ),
        Friend(
            profileImage = R.drawable.dog8,
            name = "우상욱",
            selfDescription = "나보다 안드 잘하는 사람 있으면 나와봐",
        ),
        Friend(
            profileImage = R.drawable.dog9,
            name = "배지현",
            selfDescription = "표정 풀자 ^^",
        ) ,
        Friend(
            profileImage = R.drawable.dog10,
            name = "임하늘",
            selfDescription = "?",
        ),
        Friend(
            profileImage = R.drawable.dog10,
            name = "임하늘",
            selfDescription = "!",
        )
    )
}